package com.AcidSpaceCompany.AcidSpaceFighting.Models.PrimitiveModels;

import com.AcidSpaceCompany.AcidSpaceFighting.Geometry.Point;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Массив тел. Имеет доступ к себе в качестве иттератора.
 * Всё как со списком. Можно добавлять, удалять элементы
 */
public class BodiesList<E> implements Iterable<E> {
    //Индекс базы в массиве. Если её удалить - тело не должно существовать (по идее)
    private int baseIndex;
    //Жива ли база
    private boolean isAlive_;
    public boolean isAlive()
    {
        return isAlive_;
    }

    //Собственно физические модели
    private ArrayList<E> bodies;
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
    /**
     * Получаем размер списка
     * @return Размер списка от 0 до maxSize
     */
    public int size()
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
    public Iterator<E> iterator()
    {
        return new Iterator<E>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index<length;
            }

            @Override
            public E next() {
                E result = bodies.get(indexes[index]);
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
    public E get(int index)
    {
        return bodies.get(indexes[index]);
    }

    public boolean getConnection(int bodyIndex1, int bodyIndex2)
    {
        return adjacencyMatrix[indexes[bodyIndex1]][indexes[bodyIndex2]]!=0;
    }

    /**
     * Получаем индекс модели в массиве
     * @param model  Модель для поиска
     * @return       Индекс
     */
    public int indexOf(E model)
    {
        int index = 0;
        while (index<length & !bodies.get(indexes[index]).equals(model))
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
        result[baseComponentNo-1] = new BodiesList(bodies.size(), bodies.get(realBaseIndex));
        addBodies(wasAdded, baseIndex, result[baseComponentNo-1]);
        for (int i = 0; i<length; i++)
            if (!wasAdded[i])
            {
                int realIndex = indexes[i];
                int componentNo = adjacencyMatrix[realIndex][realIndex];
                result[componentNo-1] = new BodiesList(bodies.size(), bodies.get(realIndex));
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
                listToAdd.add(bodies.get(indexes[i]), bodies.get(realIndex));
                addBodies(wasVisited, i, listToAdd);
            }
    }

    /**
     * Соединение двух те Потом необходим вызов recalculateMatrix()!
     * @param modelToAdd
     * @param modelToConnect
     */
    private void add(E modelToAdd, E modelToConnect)
    {
        add(modelToAdd);
        addConnection(indexOf(modelToConnect), indexOf(modelToAdd));
    }

    /**
     * Создает связь между двумя телами. После добавления всех связей нужно вызвать recalculateMatrix()!
     * @param bodyIndex1 Тело 1
     * @param bodyIndex2 Тело 2
     */
    public void addConnection(int bodyIndex1, int bodyIndex2)
    {
        int realIndex1 = indexes[bodyIndex1];
        int realIndex2 = indexes[bodyIndex2];
        //Помечаем наличие связи между телами единицой (потом компоненты пересчитаются в recalculateMatrix()
        adjacencyMatrix[realIndex1][realIndex2] = 1;
        adjacencyMatrix[realIndex2][realIndex1] = 1;
    }

    /**
     * Добавляем элемент в систему. После добавления всех тел нужно добавить все связи!
     * @param model Элемент для добавления
     */
    public void add(E model)
    {
        //Получаем место для соединения
        int index = getFirstFree();
        indexes[length] = index;
        bodyIndexesInIndexes[index] = length;
        bodies.set(index, model);

        //Помечаем наличие связи единичкой (потом компоненты пересчитаются в recalculateMatrix())
        adjacencyMatrix[index][index] = 1;

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
     * Создаем список тел
     * @param maxSize  Максимальный размер
     * @param firstBody  Первое тело
     */
    public BodiesList(int maxSize, E firstBody)
    {
        bodies = new ArrayList<E>(maxSize);
        indexes = new int[maxSize];
        bodyIndexesInIndexes = new int[maxSize];
        adjacencyMatrix = new int[maxSize][maxSize];
        length = 1;

        //Оно иначе при поиске первого свободного Exception выдает (ищется первый не null)
        for(int i=0; i<maxSize; i++)
        {
            bodies.add(null);
            bodyIndexesInIndexes[i] = -1;
            indexes[i]=-1;
            for (int j = 0; j<maxSize; j++)
                adjacencyMatrix[i][j] = 0;
        }

        bodies.set(0, firstBody);
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
     * Удаляем тело по индексу
     * @param bodyIndex Индекс тела для удаления
     */
    public void remove(int bodyIndex)
    {
        if (bodyIndex==baseIndex)
            isAlive_ = false;
        int realBodyIndex = indexes[bodyIndex];
        bodies.set(realBodyIndex, null);
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
        while (bodies.get(i)!=null)
            i++;
        return i;
    }
}