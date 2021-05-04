
#未混淆的类和成员
-printseeds build/seeds.txt
#列出从 apk 中删除的代码
-printusage build/unused.txt
#混淆前后的映射
-printmapping build/mapping.txt
# 保留行号，区分混淆后的同名方法，虽然会被inline影响，
-keepattributes SourceFile,LineNumberTable
# 关闭混淆时不能开启删除源文件名，会报错，
# ProGuard configuration parser error: **/proguard-rules.pro line *:* missing EOF at '''
# 删除class中的源文件名，
#-renamesourcefileattribute ''
# 不同类的成员用不同名字，同一个类还是会用相同名字，
-useuniqueclassmembernames

#各种问题通通无视
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose
-ignorewarnings
