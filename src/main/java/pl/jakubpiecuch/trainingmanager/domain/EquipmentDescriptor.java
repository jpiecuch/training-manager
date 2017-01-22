package pl.jakubpiecuch.trainingmanager.domain;

public interface EquipmentDescriptor {
    Class getConfigClass();

    interface LoadConfig {
    }

    interface RackConfig {
    }

    class BenchConfig {
        private Integer height;

        public Integer getHeight() {
            return height;
        }
    }

    class BarbellConfig {
        private boolean connectedLoad;
        private Type type;

        public boolean isConnectedLoad() {
            return this.connectedLoad;
        }

        public Type getType() {
            return type;
        }

        public enum Type {
            STRAIGHT, EZ
        }
    }

    class BarConfig {
        private Integer handles;

        public Integer getHandles() {
            return handles;
        }
    }

    class StandConfig {
        private Integer levels;
        private Range<Double> height;

        public Integer getLevels() {
            return levels;
        }

        public Range<Double> getHeight() {
            return height;
        }
    }

    class DumbbellConfig {
        private boolean connectedLoad;

        public boolean isConnectedLoad() {
            return connectedLoad;
        }
    }

    class PressConfig {
        private Integer handles;

        public Integer getHandles() {
            return handles;
        }
    }

    class Range<T> {
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
