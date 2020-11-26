package com.axgrid.plugins.axsql;

import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.StringUtils;

import java.nio.file.Path;
import java.util.ArrayList;

public final class NameUtils {

    public static String className(Path path, Path sourceRoot) {
        var p = sourceRoot.relativize(path);
        var fileName = StringUtils.removeEnd(p.getFileName().toString(), ".slot")
                .replace("-", "_");
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, fileName);
    }

//    public static String slotClassName(Path path, Path sourceRoot, String slotName) {
//        var p = sourceRoot.relativize(path);
//        var fileName = StringUtils.removeEnd(p.getFileName().toString(), ".slot")
//                .replace("-", "_");
//        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, fileName)+
//                CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, slotName)+"SlotService";
//    }

    public static String packageName(Path path, Path sourceRoot) {
        var p = sourceRoot.relativize(path).getParent();
        if (p == null) return "";
        var res = new ArrayList<String>();
        for (var i = 0; i < p.getNameCount(); i++) {
            res.add(p.getName(i).toString());
        }
        return StringUtils.join(res, ".");
    }

    public static Path javaOutputPath(Path sourcePath, Path sourceRoot, Path generatedSourcesRoot) {
        var packagePath = sourceRoot.relativize(sourcePath).getParent();
        if (packagePath == null)
            return generatedSourcesRoot.resolve(className(sourcePath, sourceRoot) + ".java");
        return generatedSourcesRoot
                .resolve(packagePath)
                .resolve(className(sourcePath, sourceRoot) + ".java");
    }

    public static Path javaGeneratedSourcePath(Path sourcePath, Path sourceRoot, Path generatedSourcesRoot) {
        var packagePath = sourceRoot.relativize(sourcePath).getParent();
        if (packagePath == null)
            return generatedSourcesRoot.resolve(className(sourcePath, sourceRoot) + ".java");

        return generatedSourcesRoot
                .resolve(packagePath)
                .resolve(className(sourcePath, sourceRoot) + ".java");
    }

}
