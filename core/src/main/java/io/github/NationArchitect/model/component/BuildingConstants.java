package io.github.NationArchitect.model.component;

/**
 * Stores shared tuning constants used by building definition enums in the component package.
 */
public class BuildingConstants {

    //Constants related to agriculture buildings
    /** Base happiness effect for agriculture buildings. */
    static final double BASE_HAPPINESS_BOOST_AG = 0.009;
    /** Base performance multiplier for agriculture buildings. */
    static final double BASE_PERFORMANCE_MULTIPLIER_AG = 0.006;
    /** Base production amount for agriculture buildings. */
    static final double BASE_PRODUCTION_AG = 400;

    //Constants related to air transport buildings
    /** Base happiness effect for air transport buildings. */
    static final double BASE_HAPPINESS_BOOST_AT = 0.035;
    /** Base performance multiplier for air transport buildings. */
    static final double BASE_PERFORMANCE_MULTIPLIER_AT = 0.011;
    /** Base education influence multiplier for air transport buildings. */
    static final double BASE_EDUCATION_PERFORMANCE_MULTIPLIER_AT = 0.012;
    /** Base industry influence multiplier for air transport buildings. */
    static final double BASE_INDUSTRY_PERFORMANCE_MULTIPLIER_AT = 0.02;

    //Constants related to education buildings
    /** Base happiness effect for education buildings. */
    static final double BASE_HAPPINESS_BOOST_ED = 0.03;
    /** Base performance multiplier for education buildings. */
    static final double BASE_PERFORMANCE_MULTIPLIER_ED = 0.005;
    /** Base education level effect for education buildings. */
    static final double BASE_EDUCATION_LEVEL_BOOST_ED = 0.07;

    //Constants related to electricity buildings
    /** Base happiness penalty for low-tier electricity buildings. */
    static final double BASE_HAPPINESS_PENALTY_EL = -0.1;
    /** Base happiness effect for advanced electricity buildings. */
    static final double BASE_HAPPINESS_BOOST_EL = 0.05;

    //Constants related to factories
    /** Base happiness penalty for factory buildings. */
    static final double BASE_HAPPINESS_PENALTY_F = -0.0015;
    /** Base component influence multiplier for factory buildings. */
    static final double BASE_COMPONENT_PERFORMANCE_MULTIPLIER_F = 0.01;

    //Constants related to health services buildings
    /** Base happiness effect for health services buildings. */
    static final double BASE_HAPPINESS_BOOST_HS = 0.012;
    /** Base performance multiplier for health services buildings. */
    static final double BASE_PERFORMANCE_MULTIPLIER_HS = 0.0035;
    /** Base health rate effect for health services buildings. */
    static final double BASE_HEALTH_RATE_BOOST_HS = 0.045;

    //Constants related to internet buildings
    /** Base happiness effect for internet buildings. */
    static final double BASE_HAPPINESS_BOOST_IN = 0.008;
    /** Base performance multiplier for internet buildings. */
    static final double BASE_PERFORMANCE_MULTIPLIER_IN = 0.007;
    /** Base office influence multiplier for internet buildings. */
    static final double BASE_OFFICE_PERFORMANCE_MULTIPLIER_IN = 0.005;
    /** Base education influence multiplier for internet buildings. */
    static final double BASE_EDUCATION_PERFORMANCE_MULTIPLIER_IN = 0.003;
    /** Base security influence multiplier for internet buildings. */
    static final double BASE_SECURITY_PERFORMANCE_MULTIPLIER_IN = 0.002;

    //Constants related to office buildings
    /** Base happiness effect for office buildings. */
    static final double BASE_HAPPINESS_BOOST_OF = 0.01;

    //Constants related to marine transport buildings
    /** Base happiness effect for marine transport buildings. */
    static final double BASE_HAPPINESS_BOOST_MT = 0.018;
    /** Base performance multiplier for marine transport buildings. */
    static final double BASE_PERFORMANCE_MULTIPLIER_MT = 0.0075;
    /** Base industry influence multiplier for marine transport buildings. */
    static final double BASE_INDUSTRY_PERFORMANCE_MULTIPLIER_MT = 0.055;

    //Constants related to rail transport buildings
    /** Base happiness effect for rail transport buildings. */
    static final double BASE_HAPPINESS_BOOST_RAIL = 0.018;
    /** Base performance multiplier for rail transport buildings. */
    static final double BASE_PERFORMANCE_MULTIPLIER_RAIL = 0.0075;
    /** Base tourism influence multiplier for rail transport buildings. */
    static final double BASE_TOURISM_PERFORMANCE_MULTIPLIER_RAIL = 0.004;
    /** Base industry influence multiplier for rail transport buildings. */
    static final double BASE_INDUSTRY_PERFORMANCE_MULTIPLIER_RAIL = 0.0055;

    //Constants related to road transport buildings
    /** Base happiness effect for road transport buildings. */
    static final double BASE_HAPPINESS_BOOST_RT = 0.018;
    /** Base performance multiplier for road transport buildings. */
    static final double BASE_PERFORMANCE_MULTIPLIER_RT = 0.0075;
    /** Base tourism influence multiplier for road transport buildings. */
    static final double BASE_TOURISM_PERFORMANCE_MULTIPLIER_RT = 0.004;
    /** Base industry influence multiplier for road transport buildings. */
    static final double BASE_INDUSTRY_PERFORMANCE_MULTIPLIER_RT = 0.0055;

    //Constants related to road network buildings
    /** Base happiness effect for road network buildings. */
    static final double BASE_HAPPINESS_BOOST_RN = 0.012;
    /** Base performance multiplier for road network buildings. */
    static final double BASE_PERFORMANCE_MULTIPLIER_RN = 0.009;
    /** Base public service influence multiplier for road network buildings. */
    static final double BASE_PUBLIC_SERVICE_PERFORMANCE_RN = 0.005;
    /** Base transport influence multiplier for road network buildings. */
    static final double BASE_TRANSPORT_PERFORMANCE_MULTIPLIER_RN = 0.004;
    /** Base industry influence multiplier for road network buildings. */
    static final double BASE_INDUSTRY_PERFORMANCE_MULTIPLIER_RN = 0.003;

    //Constants related to security buildings
    /** Base happiness effect for security buildings. */
    static final double BASE_HAPPINESS_BOOST_SC = 0.01;
    /** Base performance multiplier for security buildings. */
    static final double BASE_PERFORMANCE_MULTIPLIER_SC = 0.0025;
    /** Base crime rate effect for security buildings. */
    static final double BASE_CRIME_RATE_BOOST_SC = -0.05;

    //Constants related to tourism buildings
    /** Base happiness effect for tourism buildings. */
    static final double BASE_HAPPINESS_BOOST_TO = 0.022;
    /** Base performance multiplier for tourism buildings. */
    static final double BASE_PERFORMANCE_MULTIPLIER_TO = 0.006;

    //Constants related to water management buildings
    /** Base happiness effect for water management buildings. */
    static final double BASE_HAPPINESS_BOOST_WM = 0.01;
    /** Base performance multiplier for water management buildings. */
    static final double BASE_PERFORMANCE_MULTIPLIER_WM = 0.0045;
    /** Base health rate effect for water management buildings. */
    static final double BASE_HEALTH_RATE_BOOST_WM = 0.03;
}
