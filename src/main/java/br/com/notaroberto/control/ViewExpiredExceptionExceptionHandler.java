package br.com.notaroberto.control;

import java.io.IOException;
import java.util.Iterator;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.application.ViewExpiredException;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.RenderKit;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import org.primefaces.component.PartialViewRoot;
import org.primefaces.context.RequestContextImpl;
import org.primefaces.context.RequestContext;
import org.primefaces.util.Constants;

public class ViewExpiredExceptionExceptionHandler extends ExceptionHandlerWrapper {

    private ExceptionHandler wrapped;

    public ViewExpiredExceptionExceptionHandler() {
    }

    public ViewExpiredExceptionExceptionHandler(ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return this.wrapped;
    }

    @Override
    public void handle() throws FacesException {
        String redirectPage = null;
        for (Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator(); i.hasNext();) {
            ExceptionQueuedEvent event = i.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
            Throwable t = context.getException();
            if (t instanceof ViewExpiredException) {
                ViewExpiredException vee = (ViewExpiredException) t;
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
                try {
                    // Push some useful stuff to the request scope for use in the page
                    session.setAttribute("currentViewId", vee.getViewId());
                    redirectPage = "/app/index.xhtml";
                } finally {
                    i.remove();
                }
                doRedirect(fc, redirectPage);
                session.setAttribute("exibeDialog", true);
            }
        }

        // At this point, the queue will not contain any ViewExpiredEvents.
        // Therefore, let the parent handle them.
        getWrapped().handle();
    }

    public void doRedirect(FacesContext fc, String redirectPage) throws FacesException {
        ExternalContext ec = fc.getExternalContext();

        try {

            // workaround for PrimeFaces
            new RequestContextImpl(ec);
            if (ec.getRequestParameterMap().containsKey(Constants.PARTIAL_PROCESS_PARAM) && !ec.getRequestParameterMap().get(Constants.PARTIAL_PROCESS_PARAM).equals("@all")) {
                fc.setViewRoot(new PartialViewRoot(new UIViewRoot()));
            }

            // fix for renderer kit (Mojarra's and PrimeFaces's ajax redirect)
            if ((RequestContext.getCurrentInstance().isAjaxRequest() || fc.getPartialViewContext().isPartialRequest()) && fc.getResponseWriter() == null && fc.getRenderKit() == null) {
                ServletResponse response = (ServletResponse) ec.getResponse();
                ServletRequest request = (ServletRequest) ec.getRequest();
                response.setCharacterEncoding(request.getCharacterEncoding());

                RenderKitFactory factory = (RenderKitFactory) FactoryFinder.getFactory(FactoryFinder.RENDER_KIT_FACTORY);
                RenderKit renderKit = factory.getRenderKit(fc, fc.getApplication().getViewHandler().calculateRenderKitId(fc));
                ResponseWriter responseWriter = renderKit.createResponseWriter(response.getWriter(), null, request.getCharacterEncoding());
                fc.setResponseWriter(responseWriter);
            }
            ec.redirect(ec.getRequestContextPath() + (redirectPage != null ? redirectPage : ""));
        } catch (IOException e) {
            System.out.println("Redirect to the specified page '" + redirectPage + "' failed");
            throw new FacesException(e);
        }
    }

}
