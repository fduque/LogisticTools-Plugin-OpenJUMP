package br.puc.rio.utilities;

import br.puc.rio.telas.TelaCriarLayerDeArquivo;

import com.vividsolutions.jump.workbench.WorkbenchContext;
import com.vividsolutions.jump.workbench.plugin.AbstractPlugIn;
import com.vividsolutions.jump.workbench.plugin.EnableCheckFactory;
import com.vividsolutions.jump.workbench.plugin.MultiEnableCheck;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;

public class PlugInUploadFile extends AbstractPlugIn {
	private PlugInContext context;
	private TelaCriarLayerDeArquivo telaUploadFile;

	public static MultiEnableCheck createEnableCheck(
			WorkbenchContext workbenchContext) {
		EnableCheckFactory checkFactory = new EnableCheckFactory(
				workbenchContext);

		return new MultiEnableCheck().add(
				checkFactory.createAtLeastNLayersMustExistCheck(0)).add(
				checkFactory.createTaskWindowMustBeActiveCheck());
	}

	public boolean execute(PlugInContext context) throws Exception {
		this.context = context;
		this.telaUploadFile = new TelaCriarLayerDeArquivo(this);
		telaUploadFile.setVisible(true);
		return true;
	}

	public PlugInContext getPlugInContext() {
		return this.context;
	}
}
