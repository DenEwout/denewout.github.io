public class AnnotationProcessorTest {

    interface AnnotationProcessorGroup {
        void process(Class annotation);
    }

    interface AnnotationProcessor extends Consumer<Class> {
    }

    public interface AnnotationProcessorGroupBuilder<T> {
        void register(Class clazz, AnnotationProcessor processor);
    }

    public static class AnnotationProcessorGroupFactory {
        public static AnnotationProcessorGroup create(Consumer<AnnotationProcessorGroupBuilder> consumer,
                                                      Function<Class, AnnotationProcessor> ifAbsent) {
            HashMap<Class, AnnotationProcessor> map = new HashMap<>();
            consumer.accept(map::put);
            return clazz -> {
                map.computeIfAbsent(clazz, ifAbsent).accept(clazz);
            };
        }

    }

    public static void main(String[] args) {
        AnnotationProcessorGroup jaxRSannotationProcessorGroup = AnnotationProcessorGroupFactory.create(group -> {
            group.register(Deprecated.class,
                    annotation -> System.out.println("Found Jax-RS annotation: " + annotation));
        }, foo -> {
            throw new IllegalArgumentException("Unknown annotation");
        });

        AnnotationProcessorGroup springAnnotationProcessorGroup = AnnotationProcessorGroupFactory.create(group -> {
            group.register(Deprecated.class,
                    annotation -> System.out.println("Found Spring-MVC annotation: " + annotation));
        }, foo -> {
            throw new IllegalArgumentException("Unknown annotation");
        });

        jaxRSannotationProcessorGroup.process(Deprecated.class);
        springAnnotationProcessorGroup.process(Deprecated.class);
    }
}
