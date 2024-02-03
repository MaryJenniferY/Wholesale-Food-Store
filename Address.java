/**
 * The Address class represents a physical address with multiple lines, country, and postal code.
 * It provides methods to retrieve the individual components of the address.
 */
public class Address {

    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String country;
    private String postCode;

    /**
     * Constructs a new Address object with the specified components.
     *
     * @param addressLine1 the first line of the address
     * @param addressLine2 the second line of the address
     * @param addressLine3 the third line of the address
     * @param country      the country of the address
     * @param postCode     the postal code of the address
     */
    public Address(String addressLine1, String addressLine2, String addressLine3, String country, String postCode) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.addressLine3 = addressLine3;
        this.country = country;
        this.postCode = postCode;
    }

    /**
     * Gets the first line of the address.
     *
     * @return the first line of the address
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     * Gets the second line of the address.
     *
     * @return the second line of the address
     */
    public String getAddressLine2() {
        return addressLine2;
    }

    /**
     * Gets the third line of the address.
     *
     * @return the third line of the address
     */
    public String getAddressLine3() {
        return addressLine3;
    }

    /**
     * Gets the country of the address.
     *
     * @return the country of the address
     */
    public String getCountry() {
        return country;
    }

    /**
     * Gets the postal code of the address.
     *
     * @return the postal code of the address
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * Returns a string representation of the Address object.
     *
     * @return a string representation of the address in the format:
     * "[Address Line 1=value, Address Line 2=value, Address Line 3=value, Country=value, Postcode=value]"
     */
    @Override
    public String toString() {
        return "[Address Line 1=" + addressLine1 + ", Address Line 2=" + addressLine2 +
                ", Address Line 3=" + addressLine3 + ", Country=" + country + ", Postcode=" + postCode + "]";
    }
}
