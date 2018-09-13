package com.bird.javaagent.premain;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.LoaderClassPath;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class AgentPremain {
	public static void premain(String args, Instrumentation instrumentation) {

		instrumentation.addTransformer(new ClassFileTransformer() {
			public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
					ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
				String cname = "com.bird.javaagent.main.bean.Teacher";
				ClassPool classPool = new ClassPool();
				classPool.insertClassPath(new LoaderClassPath(loader));
				if (className.replaceAll("/", ".").equals(cname)) {
					try {
						CtClass ctClass = classPool.get(cname);

						// ���һ������
						CtField sonField = CtField.make("public String son = \"zuohui\";", ctClass);
						ctClass.addField(sonField);

						CtMethod ctMethod = ctClass.getDeclaredMethod("toString");
						ctMethod.setBody("{return name + \" is good. but he has a bad son:\" + son;}");

						return ctClass.toBytecode();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return new byte[0];
			}
		});
	}

}
