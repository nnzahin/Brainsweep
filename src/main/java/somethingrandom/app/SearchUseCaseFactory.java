package somethingrandom.app;
import somethingrandom.interfaceadapters.details.ItemDetailsController;
import somethingrandom.interfaceadapters.searchitems.SearchController;
import somethingrandom.interfaceadapters.searchitems.SearchPresenter;
import somethingrandom.interfaceadapters.searchitems.SearchViewModel;
import somethingrandom.usecase.search.SearchItemsDataAccessInterface;
import somethingrandom.usecase.search.SearchItemsInputBoundary;
import somethingrandom.usecase.search.SearchItemsInteractor;
import somethingrandom.usecase.search.SearchItemsOutputBoundary;
import somethingrandom.view.SearchView;

import java.time.Clock;

public class SearchUseCaseFactory {

    private SearchUseCaseFactory(){}

    public static SearchView create(SearchViewModel searchViewModel, SearchItemsDataAccessInterface searchDataAccessObject, ItemDetailsController detailsController) {
        SearchController searchController = createSearchUseCase(searchViewModel, searchDataAccessObject);
        return new SearchView(searchController, searchViewModel, detailsController);

    }

    private static SearchController createSearchUseCase(SearchViewModel searchViewModel, SearchItemsDataAccessInterface searchDataAccessObject) {
        SearchItemsOutputBoundary searchItemsOutputBoundary = new SearchPresenter(searchViewModel);
        Clock clock = Clock.systemUTC();
        SearchItemsInputBoundary searchInteractor = new SearchItemsInteractor(searchDataAccessObject, searchItemsOutputBoundary, clock);
        return new SearchController(searchInteractor);
    }

}
