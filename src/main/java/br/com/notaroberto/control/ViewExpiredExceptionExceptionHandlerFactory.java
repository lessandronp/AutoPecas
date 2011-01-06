package br.com.notaroberto.control;

import javax.faces.context.ExceptionHandlerFactory;
import javax.faces.context.ExceptionHandler;

public class ViewExpiredExceptionExceptionHandlerFactory extends ExceptionHandlerFactory {

    private ExceptionHandlerFactory parent;

    public ViewExpiredExceptionExceptionHandlerFactory(ExceptionHandlerFactory parent) {
        this.parent = parent;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        ExceptionHandler result = parent.getExceptionHandler();
        result = new ViewExpiredExceptionExceptionHandler(result);
        return result;
    }
}
