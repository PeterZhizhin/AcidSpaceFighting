package com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels;

import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;
import com.AcidSpaceCompany.AcidSpaceFighting.OurWorld;

public class ComplexModel extends Model {

    @Override
    public boolean getIsComplex()
    {
        return true;
    }

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

    public String getPositions() {
        String s="";
        for (Model m: models)
            s+=m.getPositions()+",";
        s=s.substring(0, s.length()-1);
        return s;
    }

    public String getSpeeds() {
        String s="";
        for (Model m: models)
            s+=m.getSpeeds()+",";
        s=s.substring(0, s.length()-1);
        return s;
    }

    public String toString() {
        String s="";
        for (Model m: models)
            s+=m.toString()+",";
        return s;
    }

    public Model getModel(int num) {
        return models.get(num);
    }

    /**
     * Возвращает модель, которой принадлежит данная точка
     * @param point Точка.
     * @return null - не принадлежит ни одной. Или модель, которой принадлежит.
     */
    @Override
    public Model getModelUnderPoint(Point point)
    {
        Model result;
        for (Model model : models)
            if ((result=model.getModelUnderPoint(point))!=null)
                return result;
        return null;
    }

    @Override
    public boolean containsPoint(Point point)
    {
        return getModelUnderPoint(point)!=null;
    }

    public void update(float time) {
        phyModel.update(time);
        //Было ли удалено хотя бы одно тело из модели
        boolean wasDeleted = false;
        for (int i=0; i<models.size(); i++) {
            if (models.get(i).getIsNoNeedMore())
            {
                models.get(i).destroy();
                OurWorld.explode(models.get(i).getCenter(), models.get(i).getMaxWidth(), models.get(i).getMaxWidth());
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
                    OurWorld.addModel(new ComplexModel(components[i]));
                }
                else
                {
                    //Если длина 1 - нужно создавать обычную модель
                    OurWorld.addModel(components[i].get(0));
                }
            //А теперь нужно изменить сам список моделей (текущий)
            //TODO: Отследить "смерть" (models.getIsAlive())
            models = components[0];
            if (models.getSize()==1)
            {
                this.setIsNoNeedMore();
                OurWorld.addModel(models.get(0));
            }
        }
    }

    public void add(Model m) {
        models.add(m);
        phyModel.recomputeMassCenters();
    }

    /**
     * Проверяет, нужно ли подсоединять данную модель к комплексной
     * @param checkModel Модель для проверки
     * @return Нужно присоединять данную модель или нет
     */
    public boolean isNeededToConnect(Model checkModel)
    {
        for (Model model : models)
            if (checkDistanceIsToConnect(checkModel, model))
                return true;
        return false;
    }

    /**
     * Проверка того, можно ли соединять модели
     * @param model1
     * @param model2
     * @return
     */
    private boolean checkDistanceIsToConnect(Model model1, Model model2)
    {

        double distance = Math.min(model1.getPhysicModel().getConnectionDistance(),
                model2.getPhysicModel().getConnectionDistance());
        //distance*=distance;
        //Расстояние между
        return model1.getPhysicModel().getCentre().getDistanceToPoint(
        //Центрами тел
                (model2.getPhysicModel().getCentre()))
        //Должно быть меньше или равно
                <=
        //Чем минимальное из расстояний, позволяющих соединять
                 distance;
    }

    public boolean checkDistanceIsToConnect(int i1, int i2) {
         return checkDistanceIsToConnect(models.get(i1), models.get(i2));
    }

    public void recalculateModels()
    {
        for (int i=0; i<models.size()-1; i++)
            for (int j = i+1; j<models.size(); j++)
                if (!checkDistanceIsToConnect(models.get(i),models.get(j)))
                   models.deleteConnection(i,j);
        recalculateComponents();
        phyModel.recomputeMassCenters();
    }

    /**
     * Должен быть вызван, когда добавление всех моделей завершено
     */
    public void additionsFinished()
    {
        //Вызываем для каждой из возможной связи в модели проверку на связь
        //(расстояние между центрами моделей меньше определенного значения)
        for (int i=0; i<models.size()-1; i++)
            for (int j = i+1; j<models.size(); j++)
                if (checkDistanceIsToConnect(models.get(i),models.get(j)))
                    models.addConnection(i,j);
        recalculateComponents();
    }

    private ComplexModel(BodiesList<Model> models)
    {
        super(null,null, 0);
        this.models = models;
        phyModel = new ComplexPhysicModel(this);
        graModel = new ComplexGraphicModel(this);
        setGraphicModel(graModel);
        setPhysicModel(phyModel);
    }

    public ComplexModel(Model firstModel) {
        super(null, null, 0);
        models=new BodiesList<Model>(maxBodiesSize, firstModel);
        phyModel = new ComplexPhysicModel(this);
        graModel = new ComplexGraphicModel(this);
        setGraphicModel(graModel);
        setPhysicModel(phyModel);
    }
}