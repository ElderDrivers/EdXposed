package com.swift.sandhook.xposedcompat.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import external.com.android.dx.Code;
import external.com.android.dx.Local;
import external.com.android.dx.TypeId;
import external.com.android.dx.rop.code.Insn;
import external.com.android.dx.rop.code.PlainInsn;
import external.com.android.dx.rop.code.RegisterSpec;
import external.com.android.dx.rop.code.RegisterSpecList;
import external.com.android.dx.rop.code.Rops;
import external.com.android.dx.rop.code.SourcePosition;
import external.com.android.dx.rop.type.Type;

public class DexMakerUtils {


    private static volatile Method addInstMethod, specMethod;

    public static void autoBoxIfNecessary(Code code, Local<Object> target, Local source) {
        String boxMethod = "valueOf";
        TypeId<?> boxTypeId;
        TypeId typeId = source.getType();
        if (typeId.equals(TypeId.BOOLEAN)) {
            boxTypeId = TypeId.get(Boolean.class);
            code.invokeStatic(boxTypeId.getMethod(boxTypeId, boxMethod, TypeId.BOOLEAN), target, source);
        } else if (typeId.equals(TypeId.BYTE)) {
            boxTypeId = TypeId.get(Byte.class);
            code.invokeStatic(boxTypeId.getMethod(boxTypeId, boxMethod, TypeId.BYTE), target, source);
        } else if (typeId.equals(TypeId.CHAR)) {
            boxTypeId = TypeId.get(Character.class);
            code.invokeStatic(boxTypeId.getMethod(boxTypeId, boxMethod, TypeId.CHAR), target, source);
        } else if (typeId.equals(TypeId.DOUBLE)) {
            boxTypeId = TypeId.get(Double.class);
            code.invokeStatic(boxTypeId.getMethod(boxTypeId, boxMethod, TypeId.DOUBLE), target, source);
        } else if (typeId.equals(TypeId.FLOAT)) {
            boxTypeId = TypeId.get(Float.class);
            code.invokeStatic(boxTypeId.getMethod(boxTypeId, boxMethod, TypeId.FLOAT), target, source);
        } else if (typeId.equals(TypeId.INT)) {
            boxTypeId = TypeId.get(Integer.class);
            code.invokeStatic(boxTypeId.getMethod(boxTypeId, boxMethod, TypeId.INT), target, source);
        } else if (typeId.equals(TypeId.LONG)) {
            boxTypeId = TypeId.get(Long.class);
            code.invokeStatic(boxTypeId.getMethod(boxTypeId, boxMethod, TypeId.LONG), target, source);
        } else if (typeId.equals(TypeId.SHORT)) {
            boxTypeId = TypeId.get(Short.class);
            code.invokeStatic(boxTypeId.getMethod(boxTypeId, boxMethod, TypeId.SHORT), target, source);
        } else if (typeId.equals(TypeId.VOID)) {
            code.loadConstant(target, null);
        } else {
            code.move(target, source);
        }
    }

    public static void autoUnboxIfNecessary(Code code, Local target, Local source,
                                            Map<TypeId, Local> tmpLocals, boolean castObj) {
        String unboxMethod;
        TypeId typeId = target.getType();
        TypeId<?> boxTypeId;
        if (typeId.equals(TypeId.BOOLEAN)) {
            unboxMethod = "booleanValue";
            boxTypeId = TypeId.get("Ljava/lang/Boolean;");
            Local boxTypedLocal = tmpLocals.get(boxTypeId);
            code.cast(boxTypedLocal, source);
            code.invokeVirtual(boxTypeId.getMethod(TypeId.BOOLEAN, unboxMethod), target, boxTypedLocal);
        } else if (typeId.equals(TypeId.BYTE)) {
            unboxMethod = "byteValue";
            boxTypeId = TypeId.get("Ljava/lang/Byte;");
            Local boxTypedLocal = tmpLocals.get(boxTypeId);
            code.cast(boxTypedLocal, source);
            code.invokeVirtual(boxTypeId.getMethod(TypeId.BYTE, unboxMethod), target, boxTypedLocal);
        } else if (typeId.equals(TypeId.CHAR)) {
            unboxMethod = "charValue";
            boxTypeId = TypeId.get("Ljava/lang/Character;");
            Local boxTypedLocal = tmpLocals.get(boxTypeId);
            code.cast(boxTypedLocal, source);
            code.invokeVirtual(boxTypeId.getMethod(TypeId.CHAR, unboxMethod), target, boxTypedLocal);
        } else if (typeId.equals(TypeId.DOUBLE)) {
            unboxMethod = "doubleValue";
            boxTypeId = TypeId.get("Ljava/lang/Double;");
            Local boxTypedLocal = tmpLocals.get(boxTypeId);
            code.cast(boxTypedLocal, source);
            code.invokeVirtual(boxTypeId.getMethod(TypeId.DOUBLE, unboxMethod), target, boxTypedLocal);
        } else if (typeId.equals(TypeId.FLOAT)) {
            unboxMethod = "floatValue";
            boxTypeId = TypeId.get("Ljava/lang/Float;");
            Local boxTypedLocal = tmpLocals.get(boxTypeId);
            code.cast(boxTypedLocal, source);
            code.invokeVirtual(boxTypeId.getMethod(TypeId.FLOAT, unboxMethod), target, boxTypedLocal);
        } else if (typeId.equals(TypeId.INT)) {
            unboxMethod = "intValue";
            boxTypeId = TypeId.get("Ljava/lang/Integer;");
            Local boxTypedLocal = tmpLocals.get(boxTypeId);
            code.cast(boxTypedLocal, source);
            code.invokeVirtual(boxTypeId.getMethod(TypeId.INT, unboxMethod), target, boxTypedLocal);
        } else if (typeId.equals(TypeId.LONG)) {
            unboxMethod = "longValue";
            boxTypeId = TypeId.get("Ljava/lang/Long;");
            Local boxTypedLocal = tmpLocals.get(boxTypeId);
            code.cast(boxTypedLocal, source);
            code.invokeVirtual(boxTypeId.getMethod(TypeId.LONG, unboxMethod), target, boxTypedLocal);
        } else if (typeId.equals(TypeId.SHORT)) {
            unboxMethod = "shortValue";
            boxTypeId = TypeId.get("Ljava/lang/Short;");
            Local boxTypedLocal = tmpLocals.get(boxTypeId);
            code.cast(boxTypedLocal, source);
            code.invokeVirtual(boxTypeId.getMethod(TypeId.SHORT, unboxMethod), target, boxTypedLocal);
        } else if (typeId.equals(TypeId.VOID)) {
            code.loadConstant(target, null);
        } else if (castObj) {
            code.cast(target, source);
        } else {
            code.move(target, source);
        }
    }

    public static Map<TypeId, Local> createResultLocals(Code code) {
        HashMap<TypeId, Local> resultMap = new HashMap<>();
        Local<Boolean> booleanLocal = code.newLocal(TypeId.BOOLEAN);
        Local<Byte> byteLocal = code.newLocal(TypeId.BYTE);
        Local<Character> charLocal = code.newLocal(TypeId.CHAR);
        Local<Double> doubleLocal = code.newLocal(TypeId.DOUBLE);
        Local<Float> floatLocal = code.newLocal(TypeId.FLOAT);
        Local<Integer> intLocal = code.newLocal(TypeId.INT);
        Local<Long> longLocal = code.newLocal(TypeId.LONG);
        Local<Short> shortLocal = code.newLocal(TypeId.SHORT);
        Local<Void> voidLocal = code.newLocal(TypeId.VOID);
        Local<Object> objectLocal = code.newLocal(TypeId.OBJECT);

        Local<Object> booleanObjLocal = code.newLocal(TypeId.get("Ljava/lang/Boolean;"));
        Local<Object> byteObjLocal = code.newLocal(TypeId.get("Ljava/lang/Byte;"));
        Local<Object> charObjLocal = code.newLocal(TypeId.get("Ljava/lang/Character;"));
        Local<Object> doubleObjLocal = code.newLocal(TypeId.get("Ljava/lang/Double;"));
        Local<Object> floatObjLocal = code.newLocal(TypeId.get("Ljava/lang/Float;"));
        Local<Object> intObjLocal = code.newLocal(TypeId.get("Ljava/lang/Integer;"));
        Local<Object> longObjLocal = code.newLocal(TypeId.get("Ljava/lang/Long;"));
        Local<Object> shortObjLocal = code.newLocal(TypeId.get("Ljava/lang/Short;"));
        Local<Object> voidObjLocal = code.newLocal(TypeId.get("Ljava/lang/Void;"));

        // backup need initialized locals
        code.loadConstant(booleanLocal, false);
        code.loadConstant(byteLocal, (byte) 0);
        code.loadConstant(charLocal, '\0');
        code.loadConstant(doubleLocal,0.0);
        code.loadConstant(floatLocal,0.0f);
        code.loadConstant(intLocal, 0);
        code.loadConstant(longLocal, 0L);
        code.loadConstant(shortLocal, (short) 0);
        code.loadConstant(voidLocal, null);
        code.loadConstant(objectLocal, null);
        // all to null
        code.loadConstant(booleanObjLocal, null);
        code.loadConstant(byteObjLocal, null);
        code.loadConstant(charObjLocal, null);
        code.loadConstant(doubleObjLocal, null);
        code.loadConstant(floatObjLocal, null);
        code.loadConstant(intObjLocal, null);
        code.loadConstant(longObjLocal, null);
        code.loadConstant(shortObjLocal, null);
        code.loadConstant(voidObjLocal, null);
        // package all
        resultMap.put(TypeId.BOOLEAN, booleanLocal);
        resultMap.put(TypeId.BYTE, byteLocal);
        resultMap.put(TypeId.CHAR, charLocal);
        resultMap.put(TypeId.DOUBLE, doubleLocal);
        resultMap.put(TypeId.FLOAT, floatLocal);
        resultMap.put(TypeId.INT, intLocal);
        resultMap.put(TypeId.LONG, longLocal);
        resultMap.put(TypeId.SHORT, shortLocal);
        resultMap.put(TypeId.VOID, voidLocal);
        resultMap.put(TypeId.OBJECT, objectLocal);

        resultMap.put(TypeId.get("Ljava/lang/Boolean;"), booleanObjLocal);
        resultMap.put(TypeId.get("Ljava/lang/Byte;"), byteObjLocal);
        resultMap.put(TypeId.get("Ljava/lang/Character;"), charObjLocal);
        resultMap.put(TypeId.get("Ljava/lang/Double;"), doubleObjLocal);
        resultMap.put(TypeId.get("Ljava/lang/Float;"), floatObjLocal);
        resultMap.put(TypeId.get("Ljava/lang/Integer;"), intObjLocal);
        resultMap.put(TypeId.get("Ljava/lang/Long;"), longObjLocal);
        resultMap.put(TypeId.get("Ljava/lang/Short;"), shortObjLocal);
        resultMap.put(TypeId.get("Ljava/lang/Void;"), voidObjLocal);

        return resultMap;
    }

    public static TypeId getObjTypeIdIfPrimitive(TypeId typeId) {
        if (typeId.equals(TypeId.BOOLEAN)) {
            return TypeId.get("Ljava/lang/Boolean;");
        } else if (typeId.equals(TypeId.BYTE)) {
            return TypeId.get("Ljava/lang/Byte;");
        } else if (typeId.equals(TypeId.CHAR)) {
            return TypeId.get("Ljava/lang/Character;");
        } else if (typeId.equals(TypeId.DOUBLE)) {
            return TypeId.get("Ljava/lang/Double;");
        } else if (typeId.equals(TypeId.FLOAT)) {
            return TypeId.get("Ljava/lang/Float;");
        } else if (typeId.equals(TypeId.INT)) {
            return TypeId.get("Ljava/lang/Integer;");
        } else if (typeId.equals(TypeId.LONG)) {
            return TypeId.get("Ljava/lang/Long;");
        } else if (typeId.equals(TypeId.SHORT)) {
            return TypeId.get("Ljava/lang/Short;");
        } else if (typeId.equals(TypeId.VOID)) {
            return TypeId.get("Ljava/lang/Void;");
        } else {
            return typeId;
        }
    }

    public static void returnRightValue(Code code, Class<?> returnType, Map<Class, Local> resultLocals) {
        String unboxMethod;
        TypeId<?> boxTypeId;
        code.returnValue(resultLocals.get(returnType));
    }

    public static void moveException(Code code, Local<?> result) {
        addInstruction(code, new PlainInsn(Rops.opMoveException(Type.THROWABLE),
                SourcePosition.NO_INFO, spec(result), RegisterSpecList.EMPTY));
    }

    public static void addInstruction(Code code, Insn insn) {
        if (addInstMethod == null) {
            try {
                addInstMethod = Code.class.getDeclaredMethod("addInstruction", Insn.class);
                addInstMethod.setAccessible(true);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        try {
            addInstMethod.invoke(code, insn);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static RegisterSpec spec(Local<?> result) {
        if (specMethod == null) {
            try {
                specMethod = Local.class.getDeclaredMethod("spec");
                specMethod.setAccessible(true);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        try {
            return (RegisterSpec) specMethod.invoke(result);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String MD5(String source) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(source.getBytes());
            return new BigInteger(1, messageDigest.digest()).toString(32);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return source;
    }
}
