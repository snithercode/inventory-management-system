package utils;


/**
 * The ValidateInput class contains methods for validating user input. It also builds an error message that can be used to
 * inform the user about which of their inputs need to be corrected and how to correct them.
 */
public class ValidateInput {

    // Declare Methods

    /**
     * Validates input for a new Product without a Company Name field and if needed, creates an error message string
     * that can be used to prompt the user for any corrections needed to their inputs.
     * @param name The name of the product.
     * @param stock The amount of stock for the product.
     * @param price The price of the product.
     * @param min The minimum amount of stock for the product.
     * @param max The maximum amount of stock for the product.
     * @return errorMessage A String containing error messages for the invalid input.
     */
    public static String validateInputProduct(String name, int stock, double price, int min, int max) {
        String errorMessage = "";

        if (name.isEmpty()) {
            errorMessage += "• Name field is empty\n";
        }

        if (stock <= 0) {
            errorMessage += "• Inventory must be a positive integer\n";
        }

        if (stock < min || stock > max) {
            errorMessage += "• Inventory value must be between min and max.\n";
        }

        if (price <= 0) {
            errorMessage += "• Price must be a positive decimal number\n";
        }

        if (max <= 0) {
            errorMessage += "• Maximum value must be a positive integer\n";
        }

        if (min <= 0) {
            errorMessage += "• Minimum value must be a positive integer\n";
        }

        if (max < min) {
            errorMessage += "• Maximum value must be greater than or equal to minimum value\n";
        }

        return errorMessage;
    }

    /**
     * Validates input for a new Outsourced Part, which requires an input in the Company Name field and if needed,
     * creates an error message string that can be used to prompt the user for any corrections needed to their inputs.
     * @param name The name of the part.
     * @param stock The amount of stock for the part.
     * @param price The price of the part.
     * @param min The minimum amount of stock for the part.
     * @param max The maximum amount of stock for the part.
     * @param companyName The name of the company who produces the part.
     * @return errorMessage A String containing error messages for the invalid input.
     */
    public static String validateInputOutsourced(String name, int stock, double price, int min, int max, String companyName) {
        String errorMessage = "";

        if (name.isEmpty()) {
            errorMessage += "• Name field is empty\n";
        }

        if (stock <= 0) {
            errorMessage += "• Inventory must be a positive integer\n";
        }

        if (stock < min || stock > max) {
            errorMessage += "• Inventory value must be between min and max.\n";
        }

        if (price <= 0) {
            errorMessage += "• Price must be a positive decimal number\n";
        }

        if (max <= 0) {
            errorMessage += "• Maximum value must be a positive integer\n";
        }

        if (min <= 0) {
            errorMessage += "• Minimum value must be a positive integer\n";
        }

        if (max < min) {
            errorMessage += "• Maximum value must be greater than or equal to minimum value\n";
        }

        if (companyName.isEmpty()) {
            errorMessage += "• Company Name must be entered.\n";
        }

        return errorMessage;
    }

    /**
     * Validates input for a new In-House Part, which requires an input to the Machine ID field. If needed,
     * creates an error message string that can be used to prompt the user for any corrections needed to their inputs.
     * @param name The name of the part.
     * @param stock The amount of stock for the part.
     * @param price The price of the part.
     * @param min The minimum amount of stock for the part.
     * @param max The maximum amount of stock for the part.
     * @param machineId The ID of the machine used to produce the part.
     * @return errorMessage A String containing error messages for the invalid input.
     */
    public static String validateInputInHouse(String name, int stock, double price, int min, int max, int machineId) {
        String errorMessage = "";

        if (name.isEmpty()) {
            errorMessage += "• Name field is empty\n";
        }

        if (stock <= 0) {
            errorMessage += "• Inventory must be a positive integer\n";
        }

        if (stock < min || stock > max) {
            errorMessage += "• Inventory value must be between min and max.\n";
        }

        if (price <= 0) {
            errorMessage += "• Price must be a positive decimal number\n";
        }

        if (max <= 0) {
            errorMessage += "• Maximum value must be a positive integer\n";
        }

        if (min <= 0) {
            errorMessage += "• Minimum value must be a positive integer\n";
        }

        if (max < min) {
            errorMessage += "• Maximum value must be greater than or equal to minimum value\n";
        }

        if (machineId <= 0) {
            errorMessage += "• Machine ID must be entered.\n";
        }

        return errorMessage;
    }
}
