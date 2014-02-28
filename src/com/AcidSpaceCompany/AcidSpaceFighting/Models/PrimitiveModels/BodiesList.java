package com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels;

import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;

import java.util.Iterator;

/**
 * Массив тел. Имеет доступ к себе в качестве иттератора.
 * Всё как со списком. Можно добавлять, удалять элементы
 */
public class BodiesList implements Iterable<PhysicModel> {
    //Индекс базы в массиве. Если её удалить - тело не должно существовать (по идее)
    private int baseIndex;
    //Жива ли база
    private boolean isAlive_;
    public boolean isAlive()
    {
        return isAlive_;
    }

    //Собственно физические модели
    private PhysicModel[] bodies;
    //Индексы тел в массиве bodies
    private int[] indexes;
    //Индексы тел из массива bodies в массиве indexes
    //-1 значит, что тела не существует
    private int[] bodyIndexesInIndexes;
    //В матрице смежности указывается то, какой компоненте принадлежит
    //0 - ни к какой компоненте не принадлежит
    private int[][] adjacencyMatrix;
    //Количество тел
    private int length;
    public int getLength()
    {
        return length;
    }

    //Количество компонент меняется при вызове recalculateMatrix()
    private int components;

    /**
     *  Вроппер для иттератора (внезапно, он имеет собственный remove())
     */
    private void deleteFromIterator(int bodyIndex)
    {
        remove(bodyIndex);
    }

    /**
     * Получаем иттератор (порядок обхода не важен)
     * @return Иттератор
     */
    public Iterator<PhysicModel> iterator()
    {
        return new Iterator<PhysicModel>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index<length;
            }

            @Override
            public PhysicModel next() {
                PhysicModel result = bodies[indexes[index]];
                index++;
                return result;
            }

            @Override
            public void remove() {
                deleteFromIterator(index - 1);
            }
        };
    }

    /**
     * Получаем элемент по индексу
     * @param index Индекс
     * @return Элемент
     */
    public PhysicModel get(int index)
    {
        return bodies[indexes[index]];
    }

    /**
     * Получаем индекс модели в массиве
     * @param model  Модель для поиска
     * @return       Индекс
     */
    public int indexOf(PhysicModel model)
    {
        int index = 0;
        while (index<length & !bodies[indexes[index]].equals(model))
            index++;
        if (index>=length)
            return -1;
        else
            return index;
    }

    /**
     * Пересчитываем матрицу (заполняем компоненты связности)
     * @return Количество компонент связности
     */
    public int recalculateMatrix()
    {
        boolean[] wasVisited = new boolean[length];
        for (int i = 0; i< wasVisited.length; i++)
            wasVisited[i] = false;
        int componentNumber = 1;
        fillMatrix(wasVisited, baseIndex, componentNumber);
        for (int i = 0; i<wasVisited.length; i++)
            if (!wasVisited[i])
            {
                componentNumber++;
                fillMatrix(wasVisited, i, componentNumber);
            }
        components = componentNumber;
        return componentNumber;
    }

    /**
     * Получаем компоненты после их перерасчета в recalculateMatrix()
     * @return
     */
    public BodiesList[] getComponents()
    {
        BodiesList[] result = new BodiesList[components];
        //Нужно получить все тела каждой компоненты по очереди
        //Для каждой компоненты создать BodiesList
        //В который добавлять по очереди все присоедененные к нему тела
        boolean[] wasAdded = new boolean[length];
        for (int i = 0; i<length; i++)
            wasAdded[i] = false;
        int realBaseIndex = indexes[baseIndex];
        int baseComponentNo = adjacencyMatrix[realBaseIndex][realBaseIndex];
        result[baseComponentNo-1] = new BodiesList(bodies.length, bodies[realBaseIndex]);
        addBodies(wasAdded, baseIndex, result[baseComponentNo-1]);
        for (int i = 0; i<length; i++)
            if (!wasAdded[i])
            {
                int realIndex = indexes[i];
                int componentNo = adjacencyMatrix[realIndex][realIndex];
                result[componentNo-1] = new BodiesList(bodies.length, bodies[realIndex]);
                addBodies(wasAdded, i, result[componentNo-1]);
            }
        return result;
    }

    private void addBodies(boolean[] wasVisited, int index, BodiesList listToAdd)
    {
        wasVisited[index] = true;
        int realIndex = indexes[index];
        for (int i=0; i<wasVisited.length; i++)
            if (!wasVisited[i] && adjacencyMatrix[indexes[i]][realIndex]!=0)
            {
                listToAdd.add(bodies[indexes[i]], bodies[realIndex]);
                addBodies(wasVisited, i, listToAdd);
            }
    }

    private void add(PhysicModel modelToAdd, PhysicModel modelToConnect)
    {
        addBody(modelToAdd, indexOf(modelToConnect));
    }

    /**
     * Добавляем тело в список, связывая его с уже существующим добавленным
     * @param bodyIndex Локальный
     */
    private void addBody(PhysicModel model, int bodyIndex)
    {
        //Получаем место для соединения
        int index = getFirstFree();
        indexes[length] = index;
        bodyIndexesInIndexes[index] = length;
        bodies[index] = model;

        int componentNo = getComponentNumber(bodyIndex);
        bodyIndex = indexes[bodyIndex];
        adjacencyMatrix[index][index] = componentNo;
        adjacencyMatrix[bodyIndex][index] = componentNo;
        adjacencyMatrix[index][bodyIndex] = componentNo;

        length++;
    }


    private void fillMatrix(boolean[] wasVisited, int index, int component)
    {
        wasVisited[index] = true;
        int realIndex = indexes[index];
        adjacencyMatrix[realIndex][realIndex] = component;
        for (int i=0; i<wasVisited.length; i++)
            if (!wasVisited[i] && adjacencyMatrix[indexes[i]][realIndex]!=0)
            {
                adjacencyMatrix[i][realIndex] = component;
                adjacencyMatrix[realIndex][i] = component;
                fillMatrix(wasVisited, i, component);
            }
    }

    /**
     * Получаем количество точек соединения (вот тут могут быть проблемы со скоростью) если что - переделывается.
     * @return Количество точек соединения
     */
    public int getConnectionPointsCount()
    {
        int result = 0;
        for (int i = 0; i<length; i++)
            result+=bodies[indexes[i]].getConnectionPointsCount();
        return result;
    }

    /**
     * Получаем точку соединения по её индексу
     * @param index Индекс
     * @return  Точка
     */
    public Point getConnectionPoint(int index)
    {
        int bodyNo = 0;
        while (index>=0)
        {
            index-=bodies[indexes[bodyNo]].getConnectionPointsCount();
            bodyNo++;
        }
        bodyNo--;
        index+=bodies[indexes[bodyNo]].getConnectionPointsCount();
        return bodies[indexes[bodyNo]].getConnectionPoint(index);
    }

    public boolean getIsConnectionPointFree(int index)
    {
        int bodyNo = 0;
        while (index>=0)
        {
            index-=bodies[indexes[bodyNo]].getConnectionPointsCount();
            bodyNo++;
        }
        bodyNo--;
        index+=bodies[indexes[bodyNo]].getConnectionPointsCount();
        return bodies[indexes[bodyNo]].getIsConnectionPointFree(index);
    }

    private int getBodyByPointIndex(int index)
    {
        int bodyNo = 0;
        while (index>=0)
        {
            index-=bodies[indexes[bodyNo]].getConnectionPointsCount();
            bodyNo++;
        }
        bodyNo--;
        return bodyNo;
    }

    /**
     * Создаем список тел
     * @param maxSize  Максимальный размер
     * @param firstBody  Первое тело
     */
    public BodiesList(int maxSize, PhysicModel firstBody)
    {
        bodies = new PhysicModel[maxSize];
        indexes = new int[maxSize];
        bodyIndexesInIndexes = new int[maxSize];
        adjacencyMatrix = new int[maxSize][maxSize];
        length = 1;

        //Оно иначе при поиске первого свободного Exception выдает (ищется первый не null)
        for(int i=0; i<maxSize; i++)
        {
            bodies[i] = null;
            bodyIndexesInIndexes[i] = -1;
            indexes[i]=-1;
            for (int j = 0; j<maxSize; j++)
                adjacencyMatrix[i][j] = 0;
        }

        bodies[0] = firstBody;
        indexes[0] = 0;
        bodyIndexesInIndexes[0] = 0;
        adjacencyMatrix[0][0] = 1;
        baseIndex = 0;
        isAlive_ = true;
        components = 1;
    }

    private int getComponentNumber(int bodyIndex)
    {
        int index = indexes[bodyIndex];
        return adjacencyMatrix[index][index];
    }

    /**
     * Получаем размер списка
     * @return Размер списка
     */
    public int getSize()
    {
        return length;
    }

    /**
     * Добавляем элемент в систему
     * @param model Элемент для добавления
     * @param connectionPointIndex  Номер точки присоединения
     */
    public void add(PhysicModel model, int connectionPointIndex)
    {
        //Получаем номер тела по точке соединения
        addBody(model, getBodyByPointIndex(connectionPointIndex));
    }

    /**
     * Удаляем тело по индексу
     * @param bodyIndex Индекс тела для удаления
     */
    public void remove(int bodyIndex)
    {
        if (bodyIndex==baseIndex)
            isAlive_ = false;
        int realBodyIndex = indexes[bodyIndex];
        bodies[realBodyIndex] = null;
        bodyIndexesInIndexes[realBodyIndex] = -1;
        //Теперь нужно сместить массив индексов вверх
        for (int i=bodyIndex; i<length-1; i++)
        {
            indexes[i] = indexes[i+1];
            bodyIndexesInIndexes[indexes[i]] = i;
        }
        //indexes[length-1] = -1;
        //Очистка матрицы смежности и массива индексов
        //(Должно работать и без этого)
        /*for (int i = 0; i<length; i++)
        {
            adjacencyMatrix[realBodyIndex][i] = 0;
            adjacencyMatrix[i][realBodyIndex] = 0;
        } */
        length--;
    }

    /**
     * Получаем первый свободный элемент в массиве тел
     * @return  Первый свободный (ненулевой) элемент в "bodies"
     */
    private int getFirstFree()
    {
        int i=0;
        while (bodies[i]!=null)
            i++;
        return i;
    }
}
