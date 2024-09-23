package GISbackend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "lands")
public class Lands {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "county", length = 50)
    private String county;

    @Column(name = "town", length = 50)
    private String town;

    @Column(name = "section_code", length = 10)
    private String sectionCode;

    @Column(name = "section_name", length = 50)
    private String sectionName;

    @Column(name = "land_number", length = 20)
    private String landNumber;

    @Column(name = "area")
    private double area;

    @Column(name = "market_value")
    private Integer marketValue;

    @Column(name = "land_value")
    private Integer landValue;

    @Column(name = "owner_name", length = 100)
    private String ownerName;

    @Column(name = "owner_type", length = 50)
    private String ownerType;

    @Column(name = "owner_id", length = 20)
    private String ownerId;

    @Column(name = "owner_share_numerator")
    private String ownerShareNumerator;

    @Column(name = "owner_share_denominator")
    private String ownerShareDenominator;

    @Column(name = "manager_name", length = 100)
    private String managerName;


    public Lands(String county, String town, String sectionCode, String sectionName, String landNumber, Integer area, Integer marketValue, Integer landValue, String ownerName, String ownerType, String ownerId, String ownerShareNumerator, String ownerShareDenominator, String managerName) {
        this.county = county;
        this.town = town;
        this.sectionCode = sectionCode;
        this.sectionName = sectionName;
        this.landNumber = landNumber;
        this.area = area;
        this.marketValue = marketValue;
        this.landValue = landValue;
        this.ownerName = ownerName;
        this.ownerType = ownerType;
        this.ownerId = ownerId;
        this.ownerShareNumerator = ownerShareNumerator;
        this.ownerShareDenominator = ownerShareDenominator;
        this.managerName = managerName;
    }
}