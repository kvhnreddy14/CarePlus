package Utils.FunctionalInterfaces;

@FunctionalInterface
public interface Mapper <T, S>{
    S map(T t);
}
