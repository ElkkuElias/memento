package memento.guistate;

import java.time.LocalDateTime;

public class Memento implements IMemento {
    private int[] options;
    private boolean isSelected;
    private final LocalDateTime saveTime;

    public Memento(int[] options, boolean isSelected) {
        this.options = options.clone(); // Copy options array
        this.isSelected = isSelected;
        this.saveTime = LocalDateTime.now();
        System.out.println("Memento created");
    }

    public int[] getOptions() {
        return options.clone(); // Return a copy of options array
    }

    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public LocalDateTime getSaveTime() {
        return saveTime;
    }
}
