package com.company.Physic;

import com.company.Geometry.Point;

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

    /**
     *  Вроппер для иттератора (внезапно он имеет собственный remove())
     */
    private void deleteFromIterator(int bodyIndex)
    {
        remove(bodyIndexesInIndexes[bodyIndex]);
    }

    /**
     * Получаем иттератор (порядок обхода не важен)
     * @return Иттератор
     */
    public Iterator<PhysicModel> iterator()
    {
        return new Iterator<PhysicModel>() {
            private int currentBodyIndex = -1;

            /**
             * Получаем следующее тело в массиве
             * @return Номер следующего тела. -1, если его не нашлось
             */
            private int getNext()
            {
                int next = currentBodyIndex+1;
                while ((next < length) && (bodies[next]==null))
                    next++;
                if (next >= length)
                    return -1;
                else
                    return next;
            }

            @Override
            public boolean hasNext() {
                return getNext()!=-1;
            }

            @Override
            public PhysicModel next() {
                currentBodyIndex = getNext();
                if (currentBodyIndex==-1)
                    return null;
                else
                    return bodies[currentBodyIndex];
            }

            @Override
            public void remove() {
                deleteFromIterator(currentBodyIndex);
            }
        };
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
        int bodyIndex = getBodyByPointIndex(connectionPointIndex);

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
