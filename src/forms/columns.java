package forms;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jayaw
 */
public class columns {
    private static Vector<String> columnNames = new Vector<>();
    
    // Initialize with default columns
    static {
        columnNames.add("ID");
        columnNames.add("Name");
        columnNames.add("Gender");
        columnNames.add("Email");
        columnNames.add("CheckIn");
        columnNames.add("CheckOut");
        columnNames.add("Work Duration");
    }
    
    /**
     * Adds a new column to the list
     * @param columnName The name of the column to add
     */
    public static void add(String columnName) {
        columnNames.add(columnName);
    }
    
    /**
     * Returns the column names as a Vector
     * @return Vector of column names
     */
    public static Vector<String> toArray() {
        return columnNames;
    }
    
    /**
     * Creates a DefaultTableModel with the current columns
     * @return DefaultTableModel configured with the columns
     */
    public static DefaultTableModel createTableModel() {
        return new DefaultTableModel(null, columnNames);
    }
    
    /**
     * Resets columns to default configuration
     */
    public static void reset() {
        columnNames.clear();
        columnNames.add("ID");
        columnNames.add("Name");
        columnNames.add("Gender");
        columnNames.add("Email");
        columnNames.add("CheckIn");
        columnNames.add("CheckOut");
        columnNames.add("Work Duration");
    }
}