package memento.guistate;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private Model model;
    private Gui gui;
    private List<IMemento> history;// Memento history


    private Stage historyStage;
    private ListView<IMemento> historyView;
    private List<IMemento> future;

    public Controller(Gui gui) {
        this.model = new Model();
        this.gui = gui;
        this.history = new ArrayList<>();
        this.future = new ArrayList<>();
    }

    public void setOption(int optionNumber, int choice) {
        saveToHistory();
        model.setOption(optionNumber, choice);

    }

    public int getOption(int optionNumber) {
        return model.getOption(optionNumber);
    }

    public void setIsSelected(boolean isSelected) {
        saveToHistory();
        model.setIsSelected(isSelected);
    }
    public void openHistoryWindow() {
        System.out.println("lol");
        Platform.runLater(() -> {
            System.out.println("lol");
            historyStage = new Stage();
            historyView = new ListView<>();
            historyStage.setScene(new Scene(historyView));
            historyView.setCellFactory(param -> new ListCell<>() {
                @Override
                protected void updateItem(IMemento item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getSaveTime().toString());
                    }
                }
            });
            historyView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    model.restoreState(newSelection);
                    gui.updateGui();
                }
            });
            historyView.getItems().setAll(history);
            historyStage.show();
        });
    }


    public boolean getIsSelected() {
        return model.getIsSelected();
    }
    public void undo() {
        if (!history.isEmpty()) {
            IMemento currentState = model.createMemento();
            future.add(currentState);
            IMemento previousState = history.remove(history.size() - 1);
            model.restoreState(previousState);
            gui.updateGui();
        }
    }
    public void redo() {
        if (!future.isEmpty()) {
            IMemento currentState = model.createMemento();
            history.add(currentState);
            IMemento nextState = future.remove(future.size() - 1);
            model.restoreState(nextState);
            gui.updateGui();
        }
    }

    private void saveToHistory() {
        IMemento currentState = model.createMemento();
        history.add(currentState);
        future.clear();
    }

}
