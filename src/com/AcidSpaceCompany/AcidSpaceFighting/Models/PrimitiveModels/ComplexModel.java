package com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels;

import com.AcidSpaceCompany.AcidSpaceFighting.World;

public class ComplexModel extends Model {


    //Максимальное их число
    //private static final int maxBodiesSize = 10;   //Для отладки
    private static final int maxBodiesSize = 1024;   //Реальное

    public ComplexGraphicModel graModel;
    public ComplexPhysicModel phyModel;
    private BodiesList<Model> models;

    public BodiesList<Model> getModels()
    {
        return models;
    }

    public int getSize() {
        return models.size();
    }

    public Model getModel(int num) {
        return models.get(num);
    }

    public void update(float time) {
        phyModel.update(time);
        //Было ли удалено хотя бы одно тело из модели
        boolean wasDeleted = false;
        for (int i=0; i<models.size(); i++) {
            if (models.get(i).getIsNoNeedMore())
            {
                models.get(i).destroy();
                World.explode(models.get(i).getCenter(), models.get(i).getMaxWidth());
                models.remove(i);
                wasDeleted = true;
            }
        }
        //Если было удалено хотя бы одно тело - запускаем пересчет компонент связности
        if (wasDeleted)
        {
            recalculateComponents();
            phyModel.recomputeMassCenters();
        }
    }

    private void recalculateComponents() {
        //Во первых, нужно выяснить, увеличилось ли число компонент связностей
        boolean needRecalculationComponents = models.recalculateMatrix()!=1;
        //Если количество компонент связностей увеличилось - нужно разъеденить эти модели
        if (needRecalculationComponents)
        {
            BodiesList<Model>[] components = models.getComponents();
            //Компонента 0 - компонента корабля игрока
            //Из остальных компонент нужно сделать модели и отправить их в "мир"
            for (int i = 1; i<components.length; i++)
                if (components[i].size()>1)
                {
                    //Если длина больше 1 - нужно создавать комплексную модель
                    World.addModel(new ComplexModel(components[i]));
                }
                else
                {
                    //Если длина 1 - нужно создавать обычную модель
                    World.addModel(components[i].get(0));
                }
            //А теперь нужно изменить сам список моделей (текущий)
            //TODO: Отследить "смерть" (models.getIsAlive())
            models = components[0];
        }
    }

    public void add(Model m, int num) {
        models.add(m, num);
        phyModel.recomputeMassCenters();
    }

    private ComplexModel(BodiesList<Model> models)
    {
        super(null,null);
        this.models = models;
        phyModel = new ComplexPhysicModel(this);
        graModel = new ComplexGraphicModel(this);
        setGraphicModel(graModel);
        setPhysicModel(phyModel);
    }

    public ComplexModel(Model firstModel) {
        super(null, null);
        models=new BodiesList<Model>(maxBodiesSize, firstModel);
        phyModel = new ComplexPhysicModel(this);
        graModel = new ComplexGraphicModel(this);
        setGraphicModel(graModel);
        setPhysicModel(phyModel);
    }
}