package io.github.NationArchitect.controller.savemanager;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import io.github.NationArchitect.model.land.Country;
import org.bson.Document;

/**
 * Serializable save payload shared by local JSON files and MongoDB documents.
 */
public class SaveData {

    private CountryState country;
    private String mapType;
    private double playTime;
    private String version;
    private SaveDateTime saveTimestamp;
    private String snapshotPath;

    public SaveData() {
    }

    public SaveData(
        CountryState country,
        String mapType,
        double playTime,
        String version,
        SaveDateTime saveTimestamp,
        String snapshotPath
    ) {
        this.country = country;
        this.mapType = mapType;
        this.playTime = playTime;
        this.version = version;
        this.saveTimestamp = saveTimestamp;
        this.snapshotPath = snapshotPath;
    }

    public static SaveData fromCountry(
        Country country,
        String mapType,
        double playTime,
        String version,
        String snapshotPath
    ) {
        return new SaveData(
            MinimalSaveStateCodec.serializeCountryState(country),
            mapType,
            playTime,
            version,
            SaveDateTime.now(),
            snapshotPath
        );
    }

    public String toJson() {
        return createJson().prettyPrint(this);
    }

    public static SaveData fromJson(String rawJson) {
        String normalizedJson = rawJson;
        if (normalizedJson != null && !normalizedJson.isEmpty() && normalizedJson.charAt(0) == '\uFEFF') {
            normalizedJson = normalizedJson.substring(1);
        }
        SaveData saveData = createJson().fromJson(SaveData.class, normalizedJson);
        if (saveData != null) {
            MinimalSaveStateCodec.hydrateCountryState(saveData.country);
        }
        return saveData;
    }

    public static Document serializeCountry(Country country) {
        CountryState countryState = MinimalSaveStateCodec.serializeCountryState(country);
        return countryState == null ? new Document() : Document.parse(createJson().toJson(countryState));
    }

    public static Country deserializeCountry(Document document) {
        if (document == null) {
            return null;
        }
        CountryState countryState = createJson().fromJson(CountryState.class, document.toJson());
        return MinimalSaveStateCodec.deserializeCountryState(countryState);
    }

    public Document dataToDocument(int slot, String username) {
        Document document = new Document();
        document.append("slot", slot);
        document.append("username", username);
        document.append("mapType", mapType);
        document.append("playTime", playTime);
        document.append("version", version);
        document.append("snapshotPath", snapshotPath);
        document.append("saveTimestamp", saveTimestamp == null ? null : Document.parse(createJson().toJson(saveTimestamp)));
        document.append("country", country == null ? null : Document.parse(createJson().toJson(country)));
        return document;
    }

    public static SaveData documentToData(Document document) {
        if (document == null) {
            return null;
        }
        Document countryDocument = document.get("country", Document.class);
        Document timestampDocument = document.get("saveTimestamp", Document.class);
        Number playTimeValue = document.get("playTime", Number.class);
        return new SaveData(
            countryDocument == null ? null : createJson().fromJson(CountryState.class, countryDocument.toJson()),
            document.getString("mapType"),
            playTimeValue == null ? 0.0 : playTimeValue.doubleValue(),
            document.getString("version"),
            timestampDocument == null ? null : createJson().fromJson(SaveDateTime.class, timestampDocument.toJson()),
            document.getString("snapshotPath")
        );
    }

    public static SaveData fromDocument(Document document) {
        return documentToData(document);
    }

    public PreviewData toPreviewData() {
        return new PreviewData(mapType, playTime, saveTimestamp, snapshotPath);
    }

    public Country restoreCountry() {
        return MinimalSaveStateCodec.deserializeCountryState(country);
    }

    public CountryState getCountry() {
        return country;
    }

    public String getCountryName() {
        return country == null ? null : country.name;
    }

    public String getMapType() {
        return mapType;
    }

    public void setMapType(String mapType) {
        this.mapType = mapType;
    }

    public double getPlayTime() {
        return playTime;
    }

    public String getVersion() {
        return version;
    }

    public SaveDateTime getSaveTimestamp() {
        return saveTimestamp;
    }

    public String getSnapshotPath() {
        return snapshotPath;
    }

    private static Json createJson() {
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        json.setTypeName(null);
        json.setUsePrototypes(false);
        json.setIgnoreUnknownFields(true);
        return json;
    }

    public static class CountryState {
        public String name;
        public PopulationState population;
        public MetricState[] metrics;
        public EconomyState economy;
        public RegionState[] regions;
    }

    public static class RegionState {
        public String name;
        public String terrainType;
        public NamedDoubleState[] undergroundResources;
        public ComponentState[] components;
        public EffectState[] activeEffects;
        public EffectState[] activePolicies;
        public double landValue;
        public double baseCrimeRate;
        public PopulationState population;
        public MetricState[] metrics;
        public EconomyState economy;
    }

    public static class PopulationState {
        public NamedIntState[] ageDistribution;
        public NamedIntState[] genderDistribution;
        public int births;
        public int deaths;
    }

    public static class MetricState {
        public String type;
        public double value;
        public double lastMonthValue;
    }

    public static class EconomyState {
        public String kind;
        public double income;
        public double expanse;
        public double balance;
        public TaxState tax;
        public NamedDoubleState[] taxRevenues;
        public NamedDoubleState[] componentBudgets;
        public ProductState[] production;
        public ProductState[] demand;
        public double treasury;
        public double importValue;
        public double exportValue;
    }

    public static class TaxState {
        public double incomeTaxRate;
        public double propertyTaxRate;
        public double vatRate;
        public double exciseTaxRate;
        public double corporateTaxRate;
        public double productionTaxRate;
    }

    public static class ComponentState {
        public String componentType;
        public String description;
        public double baseMonthlyBudget;
        public double finalMonthlyBudget;
        public double budgetMultiplier;
        public double budgetPercentage;
        public double performance;
        public double totalOccupiedLand;
        public NamedDoubleState[] relatedMetrics;
        public NamedDoubleState[] relatedComponents;
        public NamedDoubleState[] demand;
        public BuildingState[] buildings;
        public EffectState[] activeEffects;
    }

    public static class BuildingState {
        public String name;
        public String typeName;
        public int workerAmount;
        public int maxWorkerAmount;
        public double efficiency;
        public double performanceMultiplier;
        public double constructionCost;
        public double maintenanceCost;
        public double occupiedLand;
        public double production;
        public NamedDoubleState[] relatedMetrics;
        public NamedDoubleState[] relatedComponents;
        public NamedDoubleState[] demand;
    }

    public static class EffectState {
        public String name;
        public String description;
        public boolean policy;
        public boolean active;
        public int remainingMonths;
        public NamedDoubleState[] relatedComponents;
        public NamedDoubleState[] relatedMetrics;
    }

    public static class ProductState {
        public String type;
        public int amount;
        public double salePrice;
        public double purchasePrice;
    }

    public static class NamedDoubleState {
        public String key;
        public double value;

        public NamedDoubleState() {
        }

        public NamedDoubleState(String key, double value) {
            this.key = key;
            this.value = value;
        }
    }

    public static class NamedIntState {
        public String key;
        public int value;

        public NamedIntState() {
        }

        public NamedIntState(String key, int value) {
            this.key = key;
            this.value = value;
        }
    }
}
