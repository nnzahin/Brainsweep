package somethingrandom.view;
import somethingrandom.interfaceadapters.details.ItemDetailsController;
import somethingrandom.interfaceadapters.searchitems.SearchController;
import somethingrandom.interfaceadapters.searchitems.SearchState;
import somethingrandom.interfaceadapters.searchitems.SearchViewModel;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class SearchView extends JPanel implements ActionListener, PropertyChangeListener, ListSelectionListener {
    public final String viewName = "search";
    private final SearchViewModel searchViewModel;
    private final JTextField searchBar = new JTextField(15);
    private final JButton searchButton;
    private final SearchController searchController;
    private final ItemDetailsController detailsController;
    private final DefaultListModel<SearchState.Result> taskModel = new DefaultListModel<>();
    private final JList<SearchState.Result> taskList;

    public SearchView(SearchController searchController, SearchViewModel searchViewModel, ItemDetailsController detailsController) {
        this.searchController = searchController;
        this.searchViewModel = searchViewModel;
        this.detailsController = detailsController;
        searchViewModel.addPropertyChangeListener(this);

        searchController.execute("");

        SearchState searchState = searchViewModel.getState();

        // puts together search button and bar
        searchButton = new JButton(SearchViewModel.SEARCH_BUTTON_LABEL);
        JPanel searchPanel = new JPanel();
        searchPanel.add(searchBar);
        searchPanel.add(searchButton);

        this.setLayout(new BorderLayout());
        this.add(searchPanel, BorderLayout.NORTH);

        taskList = new JList<>(taskModel);
        taskList.addListSelectionListener(this);

        add(taskList, BorderLayout.CENTER);

        searchButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if (evt.getSource().equals(searchButton)) {
                        SearchState currentState = searchViewModel.getState();
                        searchController.execute(currentState.getSearchQuery());
                    }
                }
            }
        );

        searchBar.addKeyListener(
            new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    SearchState currentState = searchViewModel.getState();
                    currentState.setSearchQuery(searchBar.getText());
                    searchViewModel.setState(currentState);
                }

                @Override
                public void keyPressed(KeyEvent e) {

                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            }
        );
    }

    @Override
    public void actionPerformed(ActionEvent e)  {
        System.out.println("Click " + e.getActionCommand());

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() == searchViewModel) {
            SearchState state = (SearchState) evt.getNewValue();
            if (state.getSearchError() != null) {
                JOptionPane.showMessageDialog(this, state.getSearchError());
                return;
            }

            taskModel.removeAllElements();
            for (SearchState.Result item : state.getResults()) {
                taskModel.addElement(item);
            }
        }

    }

    @Override
    public void valueChanged(ListSelectionEvent listSelectionEvent) {
        int index = taskList.getSelectedIndex();
        if (index == -1) {
            detailsController.requestDetails(null);
            return;
        }

        SearchState.Result result = taskModel.get(index);
        detailsController.requestDetails(result.uuid());
    }
}
