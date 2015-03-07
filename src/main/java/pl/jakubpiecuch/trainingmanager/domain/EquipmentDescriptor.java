package pl.jakubpiecuch.trainingmanager.domain;

public interface EquipmentDescriptor {
    Class getConfigClass();

    public class BenchConfig {
        private Integer height;

        public Integer getHeight() {
            return height;
        }
    }

    public class  NeckConfig {
        public enum Type {
            STRAIGHT, EZ
        }
        private boolean connectedLoad;
        private Type type;

        public boolean isConnectedLoad() {
            return this.connectedLoad;
        }

        public Type getType() {
            return type;
        }
    }

    public class BarConfig {
        private Integer handles;

        public Integer getHandles() {
            return handles;
        }
    }

    public interface LoadConfig {
    }

    public class StandConfig {
        private Integer levels;
        private Range<Double> height;

        public Integer getLevels() {
            return levels;
        }

        public Range<Double> getHeight() {
            return height;
        }
    }
    public interface RackConfig {
    }

    public class DumbbellConfig {
        private boolean connectedLoad;

        public boolean isConnectedLoad() {
            return connectedLoad;
        }
    }
    public class PressConfig {
        private Integer handles;

        public Integer getHandles() {
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
