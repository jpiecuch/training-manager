package pl.jakubpiecuch.trainingmanager.domain;

import com.fasterxml.jackson.core.JsonParseException;

import java.io.IOException;

public interface EquipmentDescriptor<W> {
    Class getConfigClass();

    public class BenchConfig {
        private int height;

        public int getHeight() {
            return height;
        }
    }

    public class  NeckConfig {
        private boolean connectedLoad;
        private int type;

        public boolean isConnectedLoad() {
            return this.connectedLoad;
        }

        public int getType() {
            return type;
        }
    }

    public class BarConfig {
        private int handlesNo;

        public int getHandlesNo() {
            return handlesNo;
        }
    }

    public class LoadConfig {
    }
    public class StandConfig {
        private int levels;
        private Range<Double> height;

        public int getLevels() {
            return levels;
        }

        public Range<Double> getHeight() {
            return height;
        }
    }
    public class RackConfig {}
    public class DumbbellConfig {
        private boolean connectedLoad;

        public boolean isConnectedLoad() {
            return connectedLoad;
        }
    }
    public class PressConfig {
        private int handles;

        public int getHandles() {
            return handles;
        }
    }

    public class Range<T> {
        private T min;
        private T max;

        public T getMin() {
            return min;
        }

        public T getMax() {
            return max;
        }
    }
}
