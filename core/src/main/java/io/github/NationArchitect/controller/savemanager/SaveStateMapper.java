package io.github.NationArchitect.controller.savemanager;

import io.github.NationArchitect.model.land.Country;
import io.github.NationArchitect.model.land.Region;

/**
 * Backward-compatible facade kept so older references still compile.
 */
public final class SaveStateMapper {

    private SaveStateMapper() {
    }

    public static SaveData.CountryState serializeCountryState(Country country) {
        return SaveStateCodec.serializeCountryState(country);
    }

    public static Country deserializeCountryState(SaveData.CountryState state) {
        return SaveStateCodec.deserializeCountryState(state);
    }

    public static SaveData.RegionState serializeRegionState(Region region) {
        return SaveStateCodec.serializeRegionState(region);
    }

    public static Region deserializeRegionState(SaveData.RegionState state) {
        return SaveStateCodec.deserializeRegionState(state);
    }
}
