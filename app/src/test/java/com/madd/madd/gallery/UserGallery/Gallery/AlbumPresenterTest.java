package com.madd.madd.gallery.UserGallery.Gallery;

import android.content.ContentResolver;

import com.madd.madd.gallery.UserGallery.Album.AlbumContract;
import com.madd.madd.gallery.UserGallery.Album.AlbumPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class AlbumPresenterTest {


    AlbumPresenter presenter;

    AlbumContract.View mockedView;
    AlbumContract.Model mockedModel;

    @Before
    public void setUp() {
        mockedModel = mock(AlbumContract.Model.class);
        mockedView = mock(AlbumContract.View.class);


        presenter = new AlbumPresenter(mockedModel);
    }

    /*

        Tests:
        1: Show empty list
        2: Show not empty list
        3: Select picture with single selection
        4: Un-select picture with single selection
        5: Select picture with multiple selection
        6: Un-select picture with multiple selection

     */

    // Show picture list
    /*@Test
    public void shouldShowEmptyListMessageWhenAlbumDoesNotExists() {
        presenter.requestPictureList("this_album_does_not_exists");
        verify(mockedView).showEmptyListError();
    }
    @Test
    public void shouldShowPictureListFromExistentAlbum() {
        presenter.requestPictureList("this_album_exists");
        List<String> imageList = new ArrayList<>();
        imageList.add("0");
        imageList.add("1");
        imageList.add("2");
        verify(view).showPictureList(new ArrayList<>(),imageList);
    }


    // Single Selection
    @Test
    public void shouldRemoveSelectedPictureWhenItIsSelectedAgain(){

        List<String> selectedList = new ArrayList<>();
        selectedList.add("0");
        when(view.getSelectedPictureList()).thenReturn(selectedList);
        when(view.getMultipleSelection()).thenReturn(false);

        presenter.selectPicture("0",0);
        verify(view).showSelectedPictureCounter(0);
        assertEquals(0,view.getSelectedPictureList().size());
    }

    @Test
    public void shouldReplaceSelectedPictureWhenANewOneIsSelected(){

        List<String> selectedList = new ArrayList<>();
        selectedList.add("0");
        when(view.getSelectedPictureList()).thenReturn(selectedList);
        when(view.getMultipleSelection()).thenReturn(false);

        presenter.selectPicture("2",0);
        verify(view).showSelectedPictureCounter(1);
        assertEquals(1,view.getSelectedPictureList().size());
    }



    // Multiple Selection
    @Test
    public void shouldRemovePictureFromSelectedPictureList(){

        List<String> selectedList = new ArrayList<>();
        selectedList.add("0");
        selectedList.add("1");
        selectedList.add("2");
        when(view.getSelectedPictureList()).thenReturn(selectedList);
        when(view.getMultipleSelection()).thenReturn(true);

        presenter.selectPicture("2",0);
        verify(view).showSelectedPictureCounter(2);
        assertEquals(2,view.getSelectedPictureList().size());

    }

    @Test
    public void shouldAddNewPictureToSelectedPictureList(){

        List<String> selectedList = new ArrayList<>();
        selectedList.add("0");
        selectedList.add("1");
        selectedList.add("2");
        when(view.getSelectedPictureList()).thenReturn(selectedList);
        when(view.getMultipleSelection()).thenReturn(true);

        presenter.selectPicture("3",0);
        verify(view).showSelectedPictureCounter(4);
        assertEquals(4,view.getSelectedPictureList().size());

    }*/
}