package com.AcidSpaceCompany.AcidSpaceFighting;

import com.AcidSpaceCompany.AcidSpaceFighting.Models.Base.BaseModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.Connector.ConnectorModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.Engine.EngineModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.Gun.GunModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.ComplexModel;
import com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels.Model;
import com.AcidSpaceCompany.AcidSpaceFighting.RPSystem.Items.Bag;
import org.lwjgl.input.Keyboard;

public class SpaceShip extends ComplexModel{

    private Bag bag=new Bag();

    public Bag getBag() {
        return bag;
    }

    public int getNumber() {
        return 0;
    }

    private int[][] keyMap= new int[][]{
            {2, 3, 4, 5, 6,7 ,8 ,9 ,10},
            {16, 17, 18, 19, 20, 21, 22},
            {30, 31, 32, 33, 34, 35, 36},
            {44, 45, 46, 47, 48, 49, 50}
    };

    private Model[][] modelMap= new Model[4][9];

    public void updateKeyboardInput() {
         for (int i=0; i<4; i++) {
             for (int j=0; j<9; j++) {
                 if (modelMap[i][j]!=null)
                     if (Keyboard.isKeyDown(keyMap[i][j]))
                         modelMap[i][j].doSpecialActionA();
             }
         }
    }

    public SpaceShip(float x, float y)
    {
        super(new BaseModel(x, y, 100));
        float p2= -(float) (Math.PI/2);

        EngineModel bl=new EngineModel(-100+x, y, 100);
        EngineModel br=new EngineModel(100+x, y, 100, 2*p2);

        ConnectorModel cb=new ConnectorModel(x, 100+y, 100);
        ConnectorModel cbb=new ConnectorModel(x, 200+y, 100);
        ConnectorModel cbbb=new ConnectorModel(x, 300+y, 100);
        ConnectorModel cbbbb=new ConnectorModel(x, 400+y, 100);
        ConnectorModel cbbbbb=new ConnectorModel(x, 500+y, 100);
        EngineModel cbbbbbb=new EngineModel(x, 600+y, 100, p2);

        GunModel g = new GunModel(x, -100+y, 100);
        modelMap[1][3]=bl;
        modelMap[1][4]=g;
        modelMap[1][5]=br;
        modelMap[2][4]=cbbbbbb;

        add(g, 0);
        add(bl, 0);
        add(br, 0);
        add(cb, 0);
        add(cbb, 0);
        add(cbbb, 0);
        add(cbbbb, 0);
        add(cbbbbb, 0);
        add(cbbbbbb, 0);
    }

}
