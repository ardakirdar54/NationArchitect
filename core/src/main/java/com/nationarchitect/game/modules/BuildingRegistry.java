package com.nationarchitect.game.modules;

import com.badlogic.gdx.utils.Array;

public class BuildingRegistry {

    private static Array<Building> educationBuildings;

    public static Array<Building> getEducationBuildings() {
        if (educationBuildings == null) {
            educationBuildings = new Array<>();
            educationBuildings.add(new Building(
                "Primary School",
                "buildings/educationbuildings/primary sc.png",
                50_000, 2_000,
                Building.Category.EDUCATION,
                +8f, 0f, +3f, 0f, 0f
            ));
            educationBuildings.add(new Building(
                "Middle School",
                "buildings/educationbuildings/middleschool.png",
                80_000, 3_500,
                Building.Category.EDUCATION,
                +12f, 0f, +4f, 0f, +2f
            ));
            educationBuildings.add(new Building(
                "High School",
                "buildings/educationbuildings/highschool.png",
                120_000, 5_000,
                Building.Category.EDUCATION,
                +16f, 0f, +5f, +2f, +4f
            ));
            educationBuildings.add(new Building(
                "Research Institute",
                "buildings/educationbuildings/research.jpeg",
                250_000, 12_000,
                Building.Category.EDUCATION,
                +20f, +3f, +6f, 0f, +10f
            ));
            educationBuildings.add(new Building(
                "University",
                "buildings/educationbuildings/universitty.jpeg",
                400_000, 18_000,
                Building.Category.EDUCATION,
                +25f, +5f, +10f, +3f, +15f
            ));
        }
        return educationBuildings;
    }

    // ── HEALTH ──
    private static Array<Building> healthBuildings;
    public static Array<Building> getHealthBuildings() {
        if (healthBuildings == null) {
            healthBuildings = new Array<>();
            healthBuildings.add(new Building("Clinic",           "buildings/health/clinic.png",           60_000,  2_500, Building.Category.HEALTH, 0f,+10f,+4f,0f,0f));
            healthBuildings.add(new Building("Hospital",         "buildings/health/hospital.png",         200_000, 9_000, Building.Category.HEALTH, 0f,+18f,+6f,0f,0f));
            healthBuildings.add(new Building("Medical Center",   "buildings/health/medicalcenter.png",    350_000,14_000, Building.Category.HEALTH, +3f,+22f,+8f,0f,0f));
            healthBuildings.add(new Building("Pharmacy",         "buildings/health/pharmacy.png",          30_000, 1_200, Building.Category.HEALTH, 0f,+6f,+2f,0f,0f));
            healthBuildings.add(new Building("Research Lab",     "buildings/health/researchlab.png",      280_000,11_000, Building.Category.HEALTH, +5f,+15f,+5f,0f,+5f));
            healthBuildings.add(new Building("Mental Health Ctr","buildings/health/mentalhealthctr.png",  150_000, 6_000, Building.Category.HEALTH, 0f,+12f,+10f,0f,0f));
        }
        return healthBuildings;
    }

    // ── SECURITY ──
    private static Array<Building> securityBuildings;
    public static Array<Building> getSecurityBuildings() {
        if (securityBuildings == null) {
            securityBuildings = new Array<>();
            securityBuildings.add(new Building("Police Station",   "buildings/security/policestation.png",   80_000, 4_000, Building.Category.SECURITY, 0f,0f,+3f,+12f,0f));
            securityBuildings.add(new Building("Fire Station",     "buildings/security/firestation.png",     70_000, 3_500, Building.Category.SECURITY, 0f,+2f,+4f,+8f,0f));
            securityBuildings.add(new Building("Military Base",    "buildings/security/militarybase.png",   500_000,20_000, Building.Category.SECURITY, 0f,0f,+2f,+25f,0f));
            securityBuildings.add(new Building("Prison",           "buildings/security/prison.png",         180_000, 8_000, Building.Category.SECURITY, 0f,0f,-2f,+15f,0f));
            securityBuildings.add(new Building("Courthouse",       "buildings/security/courthouse.png",     120_000, 5_000, Building.Category.SECURITY, +2f,0f,+3f,+10f,0f));
            securityBuildings.add(new Building("Surveillance Ctr", "buildings/security/surveillancectr.png",200_000, 9_000, Building.Category.SECURITY, 0f,0f,0f,+18f,0f));
        }
        return securityBuildings;
    }

    // ── TRANSPORTATION ──
    private static Array<Building> transportationBuildings;
    public static Array<Building> getTransportationBuildings() {
        if (transportationBuildings == null) {
            transportationBuildings = new Array<>();
            // Kıyı kısıtlaması olmayan
            transportationBuildings.add(new Building("Airport",           "buildings/transportation/airport.png",         800_000,30_000, Building.Category.TRANSPORTATION, 0f,0f,+5f,+5f,+20f));
            transportationBuildings.add(new Building("Luxury Airport",    "buildings/transportation/airportlux.png",    1_200_000,45_000, Building.Category.TRANSPORTATION, 0f,0f,+8f,+5f,+25f));
            transportationBuildings.add(new Building("Bus Terminal",      "buildings/transportation/bus term.png",        120_000, 5_000, Building.Category.TRANSPORTATION, 0f,0f,+4f,+2f,+8f));
            transportationBuildings.add(new Building("Cargo Rail",        "buildings/transportation/cargo rail.png",      300_000,12_000, Building.Category.TRANSPORTATION, 0f,0f,+3f,+3f,+15f));
            transportationBuildings.add(new Building("Railway Ctrl Ctr",  "buildings/transportation/railwayvcotnrol center.png", 250_000,10_000, Building.Category.TRANSPORTATION, 0f,0f,+3f,+4f,+12f));
            transportationBuildings.add(new Building("Truck Terminal",    "buildings/transportation/turkc terminal.png",  150_000, 6_000, Building.Category.TRANSPORTATION, 0f,0f,+2f,+2f,+10f));
            transportationBuildings.add(new Building("Freight Hub",       "buildings/transportation/freig.png",           200_000, 8_000, Building.Category.TRANSPORTATION, 0f,0f,+3f,+3f,+12f));
            transportationBuildings.add(new Building("Hangar",            "buildings/transportation/hangar.png",          350_000,14_000, Building.Category.TRANSPORTATION, 0f,0f,+4f,+5f,+15f));
            // Kıyı kısıtlamalı
            transportationBuildings.add(new Building("Cargo Terminal Port","buildings/transportation/cargo terminal porrt.png", 600_000,22_000, Building.Category.TRANSPORTATION, 0f,0f,+5f,+5f,+25f, true));
            transportationBuildings.add(new Building("Commercial Port",   "buildings/transportation/commercial port.png", 700_000,25_000, Building.Category.TRANSPORTATION, 0f,0f,+6f,+5f,+28f, true));
            transportationBuildings.add(new Building("Port",              "buildings/transportation/port.png",            500_000,18_000, Building.Category.TRANSPORTATION, 0f,0f,+4f,+4f,+20f, true));
        }
        return transportationBuildings;
    }

    // ── INFRASTRUCTURE ──
    private static Array<Building> infrastructureBuildings;
    public static Array<Building> getInfrastructureBuildings() {
        if (infrastructureBuildings == null) {
            infrastructureBuildings = new Array<>();
            infrastructureBuildings.add(new Building("Agricultural Research Ctr","buildings/infrastucture/agricultural research center.png",200_000, 8_000,Building.Category.INFRASTRUCTURE,+3f,+4f,+5f,0f,+8f));
            infrastructureBuildings.add(new Building("AI Center",         "buildings/infrastucture/ai center.png",         500_000,20_000,Building.Category.INFRASTRUCTURE,+10f,0f,+4f,0f,+15f));
            infrastructureBuildings.add(new Building("Cloud Opt Center",  "buildings/infrastucture/cluod opt.png",         400_000,16_000,Building.Category.INFRASTRUCTURE,+8f,0f,+3f,0f,+12f));
            infrastructureBuildings.add(new Building("Coal Power Plant",  "buildings/infrastucture/coal power plant building.png",600_000,25_000,Building.Category.INFRASTRUCTURE,0f,-5f,0f,0f,+20f));
            infrastructureBuildings.add(new Building("Comm Tower",        "buildings/infrastucture/comm tower.png",         150_000, 6_000,Building.Category.INFRASTRUCTURE,+2f,0f,+3f,+5f,+8f));
            infrastructureBuildings.add(new Building("Data Center",       "buildings/infrastucture/data center.png",        350_000,14_000,Building.Category.INFRASTRUCTURE,+6f,0f,+2f,+4f,+12f));
            infrastructureBuildings.add(new Building("Distribution Center","buildings/infrastucture/distributioin cneter.png",250_000,10_000,Building.Category.INFRASTRUCTURE,0f,0f,+3f,+2f,+10f));
            infrastructureBuildings.add(new Building("Farm",              "buildings/infrastucture/farm1.png",               80_000, 3_000,Building.Category.INFRASTRUCTURE,0f,+6f,+5f,0f,+5f));
            infrastructureBuildings.add(new Building("Modern Farm",       "buildings/infrastucture/farm2.png",              150_000, 5_000,Building.Category.INFRASTRUCTURE,0f,+8f,+6f,0f,+8f));
            infrastructureBuildings.add(new Building("Greenhouse",        "buildings/infrastucture/greenhouse1.png",        100_000, 4_000,Building.Category.INFRASTRUCTURE,0f,+5f,+4f,0f,+6f));
            infrastructureBuildings.add(new Building("Large Greenhouse",  "buildings/infrastucture/greenhouse2.png",        180_000, 7_000,Building.Category.INFRASTRUCTURE,0f,+8f,+5f,0f,+8f));
            infrastructureBuildings.add(new Building("High Speed Rail",   "buildings/infrastucture/high speed.png",         700_000,28_000,Building.Category.INFRASTRUCTURE,0f,0f,+6f,+3f,+18f));
            infrastructureBuildings.add(new Building("Irrigation System", "buildings/infrastucture/irrigationsystem.png",   200_000, 8_000,Building.Category.INFRASTRUCTURE,0f,+5f,+4f,0f,+7f));
            infrastructureBuildings.add(new Building("Logistic Hub",      "buildings/infrastucture/logistic hub.png",       300_000,12_000,Building.Category.INFRASTRUCTURE,0f,0f,+3f,+2f,+12f));
            infrastructureBuildings.add(new Building("Metro Station",     "buildings/infrastucture/metro station.png",      400_000,16_000,Building.Category.INFRASTRUCTURE,0f,0f,+5f,+3f,+10f));
            infrastructureBuildings.add(new Building("Navigation Center", "buildings/infrastucture/navigaiton.png",         250_000,10_000,Building.Category.INFRASTRUCTURE,+3f,0f,+3f,+5f,+8f));
            infrastructureBuildings.add(new Building("Network Ctrl Ctr",  "buildings/infrastucture/netweork control center.png",350_000,14_000,Building.Category.INFRASTRUCTURE,+5f,0f,+2f,+6f,+10f));
            infrastructureBuildings.add(new Building("Network Hub",       "buildings/infrastucture/network hub.png",        300_000,12_000,Building.Category.INFRASTRUCTURE,+4f,0f,+2f,+5f,+9f));
            infrastructureBuildings.add(new Building("Nuclear Plant",     "buildings/infrastucture/nuclearplant.png",     1_500_000,50_000,Building.Category.INFRASTRUCTURE,0f,-3f,0f,0f,+40f));
            infrastructureBuildings.add(new Building("Park Complex",      "buildings/infrastucture/park complex.png",       100_000, 4_000,Building.Category.INFRASTRUCTURE,0f,+3f,+12f,0f,0f));
            infrastructureBuildings.add(new Building("Rail Logistics",    "buildings/infrastucture/rail logistic.png",      350_000,14_000,Building.Category.INFRASTRUCTURE,0f,0f,+4f,+2f,+14f));
            infrastructureBuildings.add(new Building("Road Maintenance",  "buildings/infrastucture/road mainatnnnce.png",   120_000, 5_000,Building.Category.INFRASTRUCTURE,0f,0f,+3f,+2f,+6f));
            infrastructureBuildings.add(new Building("Traffic Ctrl Ctr",  "buildings/infrastucture/traffic control center.png",200_000, 8_000,Building.Category.INFRASTRUCTURE,0f,0f,+4f,+5f,+7f));
            infrastructureBuildings.add(new Building("Wind Farm",         "buildings/infrastucture/wind farm.png",          400_000,15_000,Building.Category.INFRASTRUCTURE,0f,+2f,+4f,0f,+15f));
            infrastructureBuildings.add(new Building("Dam",               "buildings/infrastucture/dam.png",                800_000,30_000,Building.Category.INFRASTRUCTURE,0f,+6f,+4f,0f,+20f));
            infrastructureBuildings.add(new Building("Wastewater Plant",  "buildings/infrastucture/wastewater.png",         250_000,10_000,Building.Category.INFRASTRUCTURE,0f,+8f,+3f,0f,+5f));
            infrastructureBuildings.add(new Building("Water Pump",        "buildings/infrastucture/water pump.png",         150_000, 6_000,Building.Category.INFRASTRUCTURE,0f,+5f,+3f,0f,+4f));
            infrastructureBuildings.add(new Building("Water Recycle",     "buildings/infrastucture/water recycle.png",      200_000, 8_000,Building.Category.INFRASTRUCTURE,0f,+6f,+4f,0f,+5f));
            infrastructureBuildings.add(new Building("Water Treatment",   "buildings/infrastucture/water treatment.png",    180_000, 7_000,Building.Category.INFRASTRUCTURE,0f,+7f,+4f,0f,+4f));
        }
        return infrastructureBuildings;
    }
    private static Array<Building> industryBuildings;
    public static Array<Building> getIndustryBuildings() {
        if (industryBuildings == null) {
            industryBuildings = new Array<>();
            industryBuildings.add(new Building("Textile Factory", "buildings/industry/textile.png", 150_000, 6_000, Building.Category.INDUSTRY, 0f, 0f, +2f, 0f, +12f));
            industryBuildings.add(new Building("Steel Mill", "buildings/industry/steel.png", 450_000, 18_000, Building.Category.INDUSTRY, 0f, -4f, +3f, 0f, +25f));
            industryBuildings.add(new Building("Oil Refinery", "buildings/industry/refinery.png", 800_000, 32_000, Building.Category.INDUSTRY, 0f, -8f, +2f, 0f, +45f));
            industryBuildings.add(new Building("Electronics Plant", "buildings/industry/electronics.png", 600_000, 24_000, Building.Category.INDUSTRY, +5f, 0f, +4f, 0f, +30f));
        }
        return industryBuildings;
    }
}