# Glimmer AI ProGuard Rules

# Keep Android framework classes
-keep public class android.** { *; }
-keep public class androidx.** { *; }

# Keep Kotlin metadata
-keepclassmembers class ** {
    *** Companion;
}

# Keep Hilt generated code
-keep class **_MembersInjector { *; }
-keep class **_Factory { *; }
-keep class **_Provide* { *; }
-keep class dagger.** { *; }
-keep @interface dagger.** { *; }

# Keep Room database
-keep class androidx.room.** { *; }
-keep @androidx.room.Entity public class * { *; }
-keep @androidx.room.Database public class * { *; }

# Keep ONNX Runtime
-keep class org.onnxruntime.** { *; }

# Keep serializable classes
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Keep native methods
-keepclasseswithmembernames class * {
    native <methods>;
}

# Remove logging in production
-assumenosideeffects class kotlin.io.ConsoleKt {
    *** println(...);
}

# Preserve line numbers for crash reporting
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Keep Glimmer core classes
-keep class dev.glimmer.ai.core.** { *; }
-keep class dev.glimmer.ai.overlay.** { *; }
-keep class dev.glimmer.ai.ai.** { *; }
-keep class dev.glimmer.ai.memory.** { *; }
-keep class dev.glimmer.ai.plugins.** { *; }
