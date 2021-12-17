package A.B;

/**
 * This class specifies the initial configuration for Nethack that we want to launch.
 */
public class NethackConfiguration {
    public int seed = 3;
    public int rows = 50;
    public int columns = 90;
    public int roomCount = 15;

    public int minItems = 2;
    public int maxItems = 140;

    public int minMobs = 0;
    public int maxMobs = 1;

    public int initialHealth = 10;
}
