/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
#pragma once
#include <stddef.h>  // for size_t
#include <unistd.h>  // for TEMP_FAILURE_RETRY
#include <utility>
// bionic and glibc both have TEMP_FAILURE_RETRY, but eg Mac OS' libc doesn't.
#ifndef TEMP_FAILURE_RETRY
#define TEMP_FAILURE_RETRY(exp)            \
  ({                                       \
    decltype(exp) _rc;                     \
    do {                                   \
      _rc = (exp);                         \
    } while (_rc == -1 && errno == EINTR); \
    _rc;                                   \
  })
#endif
// A macro to disallow the copy constructor and operator= functions
// This must be placed in the private: declarations for a class.
//
// For disallowing only assign or copy, delete the relevant operator or
// constructor, for example:
// void operator=(const TypeName&) = delete;
// Note, that most uses of DISALLOW_ASSIGN and DISALLOW_COPY are broken
// semantically, one should either use disallow both or neither. Try to
// avoid these in new code.
#define DISALLOW_COPY_AND_ASSIGN(TypeName) \
  TypeName(const TypeName&) = delete;      \
  void operator=(const TypeName&) = delete
// A macro to disallow all the implicit constructors, namely the
// default constructor, copy constructor and operator= functions.
//
// This should be used in the private: declarations for a class
// that wants to prevent anyone from instantiating it. This is
// especially useful for classes containing only static methods.
#define DISALLOW_IMPLICIT_CONSTRUCTORS(TypeName) \
  TypeName() = delete;                           \
  DISALLOW_COPY_AND_ASSIGN(TypeName)
// The arraysize(arr) macro returns the # of elements in an array arr.
// The expression is a compile-time constant, and therefore can be
// used in defining new arrays, for example.  If you use arraysize on
// a pointer by mistake, you will get a compile-time error.
//
// One caveat is that arraysize() doesn't accept any array of an
// anonymous type or a type defined inside a function.  In these rare
// cases, you have to use the unsafe ARRAYSIZE_UNSAFE() macro below.  This is
// due to a limitation in C++'s template system.  The limitation might
// eventually be removed, but it hasn't happened yet.
// This template function declaration is used in defining arraysize.
// Note that the function doesn't need an implementation, as we only
// use its type.
template <typename T, size_t N>
char(&ArraySizeHelper(T(&array)[N]))[N];  // NOLINT(readability/casting)
#define arraysize(array) (sizeof(ArraySizeHelper(array)))
#define SIZEOF_MEMBER(t, f) sizeof(std::declval<t>().f)
// Changing this definition will cause you a lot of pain.  A majority of
// vendor code defines LIKELY and UNLIKELY this way, and includes
// this header through an indirect path.
#define LIKELY( exp )       (__builtin_expect( (exp) != 0, true  ))
#define UNLIKELY( exp )     (__builtin_expect( (exp) != 0, false ))
#define WARN_UNUSED __attribute__((warn_unused_result))
// A deprecated function to call to create a false use of the parameter, for
// example:
//   int foo(int x) { UNUSED(x); return 10; }
// to avoid compiler warnings. Going forward we prefer ATTRIBUTE_UNUSED.
template <typename... T>
void UNUSED(const T&...) {
}
// An attribute to place on a parameter to a function, for example:
//   int foo(int x ATTRIBUTE_UNUSED) { return 10; }
// to avoid compiler warnings.
#define ATTRIBUTE_UNUSED __attribute__((__unused__))
// The FALLTHROUGH_INTENDED macro can be used to annotate implicit fall-through
// between switch labels:
//  switch (x) {
//    case 40:
//    case 41:
//      if (truth_is_out_there) {
//        ++x;
//        FALLTHROUGH_INTENDED;  // Use instead of/along with annotations in
//                               // comments.
//      } else {
//        return x;
//      }
//    case 42:
//      ...
//
// As shown in the example above, the FALLTHROUGH_INTENDED macro should be
// followed by a semicolon. It is designed to mimic control-flow statements
// like 'break;', so it can be placed in most places where 'break;' can, but
// only if there are no statements on the execution path between it and the
// next switch label.
//
// When compiled with clang, the FALLTHROUGH_INTENDED macro is expanded to
// [[clang::fallthrough]] attribute, which is analysed when performing switch
// labels fall-through diagnostic ('-Wimplicit-fallthrough'). See clang
// documentation on language extensions for details:
// http://clang.llvm.org/docs/LanguageExtensions.html#clang__fallthrough
//
// When used with unsupported compilers, the FALLTHROUGH_INTENDED macro has no
// effect on diagnostics.
//
// In either case this macro has no effect on runtime behavior and performance
// of code.
#ifndef FALLTHROUGH_INTENDED
#define FALLTHROUGH_INTENDED [[clang::fallthrough]]  // NOLINT
#endif
// Current ABI string
#if defined(__arm__)
#define ABI_STRING "arm"
#elif defined(__aarch64__)
#define ABI_STRING "arm64"
#elif defined(__i386__)
#define ABI_STRING "x86"
#elif defined(__x86_64__)
#define ABI_STRING "x86_64"
#elif defined(__mips__) && !defined(__LP64__)
#define ABI_STRING "mips"
#elif defined(__mips__) && defined(__LP64__)
#define ABI_STRING "mips64"
#endif