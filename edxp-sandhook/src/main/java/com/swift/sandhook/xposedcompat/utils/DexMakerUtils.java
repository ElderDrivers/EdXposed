package com.swift.sandhook.xposedcompat.utils;
import com.elderdrivers.riru.edxp.config.ConfigManager;
import com.elderdrivers.riru.edxp.util.Utils;

import java.io.File;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import external.com.android.dx.Code;
import external.com.android.dx.Local;
import external.com.android.dx.TypeId;
/*
Copyright 2021 ganyao swift_gan@trendmicro.com.cn

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

/*
Apache License
Version 2.0, January 2004
http://www.apache.org/licenses/

TERMS AND CONDITIONS FOR USE, REPRODUCTION, AND DISTRIBUTION

Definitions
"License" shall mean the terms and conditions for use, reproduction, and distribution as defined by Sections 1 through 9 of this document.

"Licensor" shall mean the copyright owner or entity authorized by the copyright owner that is granting the License.

"Legal Entity" shall mean the union of the acting entity and all other entities that control, are controlled by, or are under common control with that entity. For the purposes of this definition, "control" means (i) the power, direct or indirect, to cause the direction or management of such entity, whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or (iii) beneficial ownership of such entity.

"You" (or "Your") shall mean an individual or Legal Entity exercising permissions granted by this License.

"Source" form shall mean the preferred form for making modifications, including but not limited to software source code, documentation source, and configuration files.

"Object" form shall mean any form resulting from mechanical transformation or translation of a Source form, including but not limited to compiled object code, generated documentation, and conversions to other media types.

"Work" shall mean the work of authorship, whether in Source or Object form, made available under the License, as indicated by a copyright notice that is included in or attached to the work (an example is provided in the Appendix below).

"Derivative Works" shall mean any work, whether in Source or Object form, that is based on (or derived from) the Work and for which the editorial revisions, annotations, elaborations, or other modifications represent, as a whole, an original work of authorship. For the purposes of this License, Derivative Works shall not include works that remain separable from, or merely link (or bind by name) to the interfaces of, the Work and Derivative Works thereof.

"Contribution" shall mean any work of authorship, including the original version of the Work and any modifications or additions to that Work or Derivative Works thereof, that is intentionally submitted to Licensor for inclusion in the Work by the copyright owner or by an individual or Legal Entity authorized to submit on behalf of the copyright owner. For the purposes of this definition, "submitted" means any form of electronic, verbal, or written communication sent to the Licensor or its representatives, including but not limited to communication on electronic mailing lists, source code control systems, and issue tracking systems that are managed by, or on behalf of, the Licensor for the purpose of discussing and improving the Work, but excluding communication that is conspicuously marked or otherwise designated in writing by the copyright owner as "Not a Contribution."

"Contributor" shall mean Licensor and any individual or Legal Entity on behalf of whom a Contribution has been received by Licensor and subsequently incorporated within the Work.

Grant of Copyright License. Subject to the terms and conditions of this License, each Contributor hereby grants to You a perpetual, worldwide, non-exclusive, no-charge, royalty-free, irrevocable copyright license to reproduce, prepare Derivative Works of, publicly display, publicly perform, sublicense, and distribute the Work and such Derivative Works in Source or Object form.
Grant of Patent License. Subject to the terms and conditions of this License, each Contributor hereby grants to You a perpetual, worldwide, non-exclusive, no-charge, royalty-free, irrevocable (except as stated in this section) patent license to make, have made, use, offer to sell, sell, import, and otherwise transfer the Work, where such license applies only to those patent claims licensable by such Contributor that are necessarily infringed by their Contribution(s) alone or by combination of their Contribution(s) with the Work to which such Contribution(s) was submitted. If You institute patent litigation against any entity (including a cross-claim or counterclaim in a lawsuit) alleging that the Work or a Contribution incorporated within the Work constitutes direct or contributory patent infringement, then any patent licenses granted to You under this License for that Work shall terminate as of the date such litigation is filed.
Redistribution. You may reproduce and distribute copies of the Work or Derivative Works thereof in any medium, with or without modifications, and in Source or Object form, provided that You meet the following conditions:
You must give any other recipients of the Work or Derivative Works a copy of this License; and
You must cause any modified files to carry prominent notices stating that You changed the files; and
You must retain, in the Source form of any Derivative Works that You distribute, all copyright, patent, trademark, and attribution notices from the Source form of the Work, excluding those notices that do not pertain to any part of the Derivative Works; and
If the Work includes a "NOTICE" text file as part of its distribution, then any Derivative Works that You distribute must include a readable copy of the attribution notices contained within such NOTICE file, excluding those notices that do not pertain to any part of the Derivative Works, in at least one of the following places: within a NOTICE text file distributed as part of the Derivative Works; within the Source form or documentation, if provided along with the Derivative Works; or, within a display generated by the Derivative Works, if and wherever such third-party notices normally appear. The contents of the NOTICE file are for informational purposes only and do not modify the License. You may add Your own attribution notices within Derivative Works that You distribute, alongside or as an addendum to the NOTICE text from the Work, provided that such additional attribution notices cannot be construed as modifying the License.
You may add Your own copyright statement to Your modifications and may provide additional or different license terms and conditions for use, reproduction, or distribution of Your modifications, or for any such Derivative Works as a whole, provided Your use, reproduction, and distribution of the Work otherwise complies with the conditions stated in this License.

Submission of Contributions. Unless You explicitly state otherwise, any Contribution intentionally submitted for inclusion in the Work by You to the Licensor shall be under the terms and conditions of this License, without any additional terms or conditions. Notwithstanding the above, nothing herein shall supersede or modify the terms of any separate license agreement you may have executed with Licensor regarding such Contributions.
Trademarks. This License does not grant permission to use the trade names, trademarks, service marks, or product names of the Licensor, except as required for reasonable and customary use in describing the origin of the Work and reproducing the content of the NOTICE file.
Disclaimer of Warranty. Unless required by applicable law or agreed to in writing, Licensor provides the Work (and each Contributor provides its Contributions) on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied, including, without limitation, any warranties or conditions of TITLE, NON-INFRINGEMENT, MERCHANTABILITY, or FITNESS FOR A PARTICULAR PURPOSE. You are solely responsible for determining the appropriateness of using or redistributing the Work and assume any risks associated with Your exercise of permissions under this License.
Limitation of Liability. In no event and under no legal theory, whether in tort (including negligence), contract, or otherwise, unless required by applicable law (such as deliberate and grossly negligent acts) or agreed to in writing, shall any Contributor be liable to You for damages, including any direct, indirect, special, incidental, or consequential damages of any character arising as a result of this License or out of the use or inability to use the Work (including but not limited to damages for loss of goodwill, work stoppage, computer failure or malfunction, or any and all other commercial damages or losses), even if such Contributor has been advised of the possibility of such damages.
Accepting Warranty or Additional Liability. While redistributing the Work or Derivative Works thereof, You may choose to offer, and charge a fee for, acceptance of support, warranty, indemnity, or other liability obligations and/or rights consistent with this License. However, in accepting such obligations, You may act only on Your own behalf and on Your sole responsibility, not on behalf of any other Contributor, and only if You agree to indemnify, defend, and hold each Contributor harmless for any liability incurred by, or claims asserted against, such Contributor by reason of your accepting any such warranty or additional liability.
END OF TERMS AND CONDITIONS
 */
public class DexMakerUtils {

    public static boolean canCache = true;

    static {
        File cacheDir = new File(ConfigManager.getCachePath(""));
        if(!cacheDir.canRead() || !cacheDir.canWrite()) {
            Utils.logW("Cache disabled");
            canCache = false;
        }
    }

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

    public static String getSha1Hex(String text) {
        final MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-1");
            byte[] result = digest.digest(text.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : result) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            DexLog.e("error hashing target method: " + text, e);
        }
        return "";
    }
}
