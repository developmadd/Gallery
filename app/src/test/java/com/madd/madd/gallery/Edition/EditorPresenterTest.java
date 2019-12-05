package com.madd.madd.gallery.Edition;

import com.madd.madd.gallery.Edition.Editor.EditorContract;
import com.madd.madd.gallery.Edition.Editor.EditorPresenter;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EditorPresenterTest {


    EditorContract.View mockedView;
    EditorPresenter presenter;

    @Before
    public void setUp(){

        mockedView = mock(EditorContract.View.class);

        presenter = new EditorPresenter();
        presenter.setView(mockedView);

    }


    /*

        Tests:
        1: Select picture
        2: Drag picture
        3: Delete picture with remaining pictures in list
        4: Delete picture with no remaining pictures in list
        5: Update picture

     */

    @Test
    public void selectPicture(){

        List<String> pictureList = new ArrayList<>();
        pictureList.add("1");
        pictureList.add("2");
        pictureList.add("3");
        int selectPictureIndex = 0;

        when(mockedView.getPictureList()).thenReturn(pictureList);

        verify(mockedView).loadPicture(pictureList.get(selectPictureIndex));
        verify(mockedView).showEditionButton();

    }


    @Test
    public void dragPicture(){

        List<String> pictureList = new ArrayList<>();
        pictureList.add("1");
        pictureList.add("2");
        List<String> swapPictureList = new ArrayList<>();
        swapPictureList.add("2");
        swapPictureList.add("1");
        int fromPosition = 0, toPosition = 1;

        when(mockedView.getPictureList()).thenReturn(pictureList);
        presenter.dragPicture(fromPosition,toPosition);

        verify(mockedView).refreshPictures(fromPosition,toPosition);
        assertEquals(mockedView.getPictureList(),swapPictureList);

    }

    @Test
    public void deletePictureAndRemainingPictures(){

        List<String> pictureList = new ArrayList<>();
        pictureList.add("1");
        pictureList.add("2");
        pictureList.add("3");
        int selectPictureIndex = 0;
        int initialPictureListSize = pictureList.size();

        when(mockedView.getPictureList()).thenReturn(pictureList);
        when(mockedView.getSelectedPicture()).thenReturn(selectPictureIndex);
        presenter.editPicture("");

        assertEquals(mockedView.getPictureList().size(),initialPictureListSize - 1);
        verify(mockedView).loadPicture(pictureList.get(0));
        verify(mockedView).showEditionButton();

    }

    @Test
    public void deletePictureAndNoRemainingPictures(){

        List<String> pictureList = new ArrayList<>();
        pictureList.add("1");

        int selectPictureIndex = 0;
        int initialPictureListSize = pictureList.size();

        when(mockedView.getPictureList()).thenReturn(pictureList);
        when(mockedView.getSelectedPicture()).thenReturn(selectPictureIndex);
        presenter.editPicture("");

        assertEquals(mockedView.getPictureList().size(),initialPictureListSize - 1);
        verify(mockedView).returnSelectedPictureList();

    }


    @Test
    public void updatePicture(){

        List<String> pictureList = new ArrayList<>();
        pictureList.add("1");
        pictureList.add("2");
        List<String> updatedPictureList = new ArrayList<>();
        updatedPictureList.add("3");
        updatedPictureList.add("2");

        int selectPictureIndex = 0;

        when(mockedView.getPictureList()).thenReturn(pictureList);
        when(mockedView.getSelectedPicture()).thenReturn(selectPictureIndex);
        presenter.editPicture("3");

        assertEquals(updatedPictureList,pictureList);
        verify(mockedView).refreshPicture(selectPictureIndex);

    }

}