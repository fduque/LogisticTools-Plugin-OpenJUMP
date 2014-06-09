package br.puc.rio.pontoCentral;

import br.puc.rio.telas.TelaPontoCentral;

import com.vividsolutions.jump.workbench.plugin.AbstractPlugIn;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;


public class PontoCentralPlugIn extends AbstractPlugIn {

	private PlugInContext context;	
	private TelaPontoCentral form;

    public boolean execute(PlugInContext context) throws Exception{
    	this.context = context;
    	this.form = new TelaPontoCentral(this);
    	form.setVisible(true);
        return true;
    }
    public PlugInContext getPlugInContext() {
    	return context; 
    }
	

}
