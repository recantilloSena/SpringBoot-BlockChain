/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jug.blockchain.view;

import com.jug.blockchain.config.BlockProperties;
import com.jug.blockchain.services.SumaService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author RICARDO
 */
@Route("blockchain")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@PageTitle("Smart Contract Call in Java")
public class MainView extends VerticalLayout{

    
    private final TextField numero = new TextField("Cantidad a Sumar");
    private final Button blockCall = new Button("Blockchain Process");
    
    //Visualizar los Resultados del Procesamiento y del Bloque
    private final HorizontalLayout  hlResultados = new  HorizontalLayout();
    private final Grid<BlockProperties> gridBlock = new Grid();
      
    private final List<BlockProperties> bloques = new ArrayList<>();
        
    
     @Autowired
     public MainView(SumaService sumaService) {
        
                 
         add(numero,blockCall, hlResultados);
         
         hlResultados.add(gridBlock);
         gridSetup();
         
         
         blockCall.addClickListener((t) -> {
             
             BigInteger bigIntegerStr = new BigInteger(numero.getValue());
             
             if ( bigIntegerStr.longValue() > 0  || numero.getValue() == null ) {
                 try {
                     sumaService.setData( bigIntegerStr ); //Call de BlockChain Service
                    
                     BlockProperties bloque = new BlockProperties();
                     
                     bloque.setAcumulado(sumaService.getData());
                     bloque.setBlockNumber(sumaService.getBlockNumber());
                    
                     bloques.add(bloque);
                     gridBlock.getDataProvider().refreshAll();
                     
                 } catch (Exception ex) {
                     Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
                     Notification.show("Digte un Valor Correcto");
                 }
             }
             
         });
         
         
         
        
        
    }

    public final void gridSetup() {
        gridBlock.removeAllColumns();
        gridBlock.addColumn(BlockProperties::getBlockNumber)
                 .setHeader("Block Number")
                 .setWidth("200px");
        
        gridBlock.addColumn(BlockProperties::getAcumulado)
                  .setHeader("Smart Total")
                  .setWidth("200px") ;
        
        
        
        //Initial Block
        BlockProperties bloque = new BlockProperties();
        bloque.setBlockNumber("Deploy Contract");
        bloque.setAcumulado(BigInteger.valueOf(0));
        
        bloques.add(bloque);
        
        gridBlock.setItems(bloques);
        gridBlock.setWidth("400px");
        gridBlock.setHeight("400px");
                
        
    }
    
    
    
    
    
}
