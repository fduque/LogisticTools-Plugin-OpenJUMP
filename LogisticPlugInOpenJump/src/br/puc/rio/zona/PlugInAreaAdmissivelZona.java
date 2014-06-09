package br.puc.rio.zona;

import br.puc.rio.telas.TelaZonaAreaAdmissivel;

import com.vividsolutions.jump.workbench.WorkbenchContext;
import com.vividsolutions.jump.workbench.plugin.AbstractPlugIn;
import com.vividsolutions.jump.workbench.plugin.EnableCheckFactory;
import com.vividsolutions.jump.workbench.plugin.MultiEnableCheck;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;

public class PlugInAreaAdmissivelZona extends AbstractPlugIn {

	private PlugInContext context;
	private TelaZonaAreaAdmissivel telaZonaAreaAdmissivel;
	

	/*
	Esse método é nativo da classe AbstractPlugin e define que para carregar esse plugin é necessário ter ao menos um camada (Layer) carregada no OpenJUMP.
*/		
	public static MultiEnableCheck createEnableCheck(
			WorkbenchContext workbenchContext) {
		EnableCheckFactory checkFactory = new EnableCheckFactory(
				workbenchContext);

		return new MultiEnableCheck().add(
				checkFactory.createAtLeastNLayersMustExistCheck(0)).add(
				checkFactory.createTaskWindowMustBeActiveCheck());
	}

	/*
	Esse método é nativo da classe AbstractPlugin. 
	Ele é acionado automaticamente toda vez que o PlugIn é instanciado.
	 Nesse caso o método cria a Tela para entrada de dados do procedimento de formação de subzonas.
*/	
public boolean execute(PlugInContext context) throws Exception {
		this.context = context;

		this.telaZonaAreaAdmissivel = new TelaZonaAreaAdmissivel(this);
		telaZonaAreaAdmissivel.setVisible(true);

		return true;
	}

	public PlugInContext getPlugInContext() {
		return this.context;
	}

}
