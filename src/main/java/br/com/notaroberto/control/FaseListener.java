package br.com.notaroberto.control;

import java.io.IOException;

import br.com.notaroberto.model.Usuario;
import br.com.notaroberto.util.Constantes;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.RenderKit;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.primefaces.component.PartialViewRoot;

import org.primefaces.context.RequestContext;
import org.primefaces.context.RequestContextImpl;
import org.primefaces.util.Constants;

/**
 * 
 * @author Lessandro
 */
public class FaseListener implements PhaseListener {

    private static final long serialVersionUID = 1L;

    public FaseListener() {
    }

    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext fc = event.getFacesContext();
        ExternalContext ec = fc.getExternalContext();


        if (StringUtils.isNotBlank(Constantes.PAGINA_INDEX)) {
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
                HttpSession session = (HttpSession) ec.getSession(true);
                Usuario usuario = (Usuario) session.getAttribute("usuarioAutenticado");
                if ((usuario != null && !usuario.getPerfil().getIdPerfil().equals(Constantes.ID_PERFIL_ADMINISTRADOR)) && (fc.getViewRoot().getViewId().contains(Constantes.PAGINA_EXIBIR_PERFIL) || fc.getViewRoot().getViewId().contains(Constantes.PAGINA_EXIBIR_USUARIO))) {
                    ec.redirect(ec.getRequestContextPath() + "/app" + Constantes.PAGINA_INDEX);
                }
            } catch (IOException e) {
                System.out.println("Redirect to the specified login page " + Constantes.PAGINA_INDEX + " failed");
                throw new FacesException(e);
            }
        }
    }

    @Override
    public void beforePhase(PhaseEvent event) {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}
