package com.madd.madd.gallery.UI.Fragments.Gallery;


import com.madd.madd.gallery.UI.Fragments.Album.AlbumContract;
import com.madd.madd.gallery.UI.Fragments.Album.AlbumPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
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
        presenter.setView(mockedView);
    }

    /*

        Tests:
        1: Show empty list
        2: Show not empty list
        3: Select picture with single selection and picture pre selected
        4: Un-select picture with single selection
        5: Select picture with multiple selection and picture pre selected
        6: Un-select picture with multiple selection

     */


    @Test
    public void showEmptyAlbum() {
        doAnswer(invocation -> {
            ((AlbumContract.Model.PictureListRequest)invocation.getArguments()[1]).onError("");
            return null;
        }).when(mockedModel).getPictureList(eq("album_name"),any(AlbumContract.Model.PictureListRequest.class));

        presenter.requestPictureList("album_name");
        verify(mockedView).showEmptyListError();
    }

    @Test
    public void showNotEmptyAlbum() {
        List<String> selectedPictureList = new ArrayList<>();
        List<String> albumPictureList = new ArrayList<>();
        doAnswer(invocation -> {
            ((AlbumContract.Model.PictureListRequest)invocation.getArguments()[1]).onSuccess(albumPictureList);
            return null;
        }).when(mockedModel).getPictureList(eq("album_name"),any(AlbumContract.Model.PictureListRequest.class));

        presenter.requestPictureList("album_name");
        verify(mockedView).showPictureList(selectedPictureList,albumPictureList);

    }



    @Test
    public void selectPictureWithSingleSelectionAndPicturePreSelected(){

        List<String> albumPictureList = new ArrayList<>();
        albumPictureList.add("1");
        albumPictureList.add("2");
        albumPictureList.add("3");

        int selectedIndexByUser = 1;
        int preSelectedIndexByUser = 0;

        List<String> selectedPictureList = new ArrayList<>();
        selectedPictureList.add(albumPictureList.get(preSelectedIndexByUser));

        when(mockedView.getMultipleSelection()).thenReturn(false);
        when(mockedView.getSelectedPictureList()).thenReturn(selectedPictureList);
        when(mockedView.getAlbumPictureList()).thenReturn(albumPictureList);

        presenter.selectPicture(albumPictureList.get(selectedIndexByUser),selectedIndexByUser);
        verify(mockedView).refreshPictureAtPosition(preSelectedIndexByUser);
        verify(mockedView).showSelectedPictureCounter(1);
        verify(mockedView).refreshPictureAtPosition(selectedIndexByUser);

    }

    @Test
    public void unSelectPictureWithSingleSelection(){

        List<String> albumPictureList = new ArrayList<>();
        albumPictureList.add("1");
        albumPictureList.add("2");
        albumPictureList.add("3");

        int selectedIndexByUser = 0;
        int preSelectedIndexByUser = 0;

        List<String> selectedPictureList = new ArrayList<>();
        selectedPictureList.add(albumPictureList.get(preSelectedIndexByUser));

        when(mockedView.getMultipleSelection()).thenReturn(false);
        when(mockedView.getSelectedPictureList()).thenReturn(selectedPictureList);
        when(mockedView.getAlbumPictureList()).thenReturn(albumPictureList);

        presenter.selectPicture(albumPictureList.get(selectedIndexByUser),selectedIndexByUser);
        verify(mockedView).showSelectedPictureCounter(0);
        verify(mockedView).refreshPictureAtPosition(selectedIndexByUser);

    }



    @Test
    public void selectPictureWithMultipleSelectionAndPicturePreSelected(){

        List<String> albumPictureList = new ArrayList<>();
        albumPictureList.add("1");
        albumPictureList.add("2");
        albumPictureList.add("3");

        int selectedIndexByUser = 1;
        int preSelectedIndexByUser = 0;

        List<String> selectedPictureList = new ArrayList<>();
        selectedPictureList.add(albumPictureList.get(preSelectedIndexByUser));
        int initialSelectedPictures = selectedPictureList.size();

        when(mockedView.getMultipleSelection()).thenReturn(true);
        when(mockedView.getSelectedPictureList()).thenReturn(selectedPictureList);

        presenter.selectPicture(albumPictureList.get(selectedIndexByUser),selectedIndexByUser);

        verify(mockedView).showSelectedPictureCounter(initialSelectedPictures + 1);
        assertEquals(mockedView.getSelectedPictureList().size(),initialSelectedPictures + 1);
        verify(mockedView).refreshPictureAtPosition(selectedIndexByUser);

    }

    @Test
    public void unSelectPictureWithMultipleSelectionAndPicturePreSelected(){

        List<String> albumPictureList = new ArrayList<>();
        albumPictureList.add("1");
        albumPictureList.add("2");
        albumPictureList.add("3");

        int selectedIndexByUser = 0;
        int preSelectedIndexByUser = 0;

        List<String> selectedPictureList = new ArrayList<>();
        selectedPictureList.add(albumPictureList.get(preSelectedIndexByUser));
        int initialSelectedPictures = selectedPictureList.size();

        when(mockedView.getMultipleSelection()).thenReturn(true);
        when(mockedView.getSelectedPictureList()).thenReturn(selectedPictureList);

        presenter.selectPicture(albumPictureList.get(selectedIndexByUser),selectedIndexByUser);

        verify(mockedView).showSelectedPictureCounter(initialSelectedPictures - 1);
        assertEquals(mockedView.getSelectedPictureList().size(),initialSelectedPictures - 1);
        verify(mockedView).refreshPictureAtPosition(selectedIndexByUser);

    }
}