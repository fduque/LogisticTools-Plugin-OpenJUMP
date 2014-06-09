package br.puc.rio.openJumpExt;

import com.vividsolutions.jump.workbench.plugin.Extension;
import com.vividsolutions.jump.workbench.plugin.PlugInContext;

public class CarregamentoRedeExtension extends Extension{
	/**
	 *
	 **/
	/*
Essa classe herda de extensions que permite criar um PlugIn a ser adicionado ao OpenJump.
O método configure será executado quando o OpenJump for aberto. Esse método irá adicionar os items de menu a barra de menu do OpenJump. A partir dessa barra é possível acessar
o menu do PlugIn e escolher qual funcionalidade executar.
	*/	
    public void configure(PlugInContext context) throws Exception{ 
    	new PlugInWindowManager().initialize(context);
    }  
}


