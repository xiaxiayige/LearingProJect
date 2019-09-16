/*
 * Copyright (C) 2011 The Android Open Source Project
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


namespace art {

    union JValue;
    class OatQuickMethodHeader;
    class ProfilingInfo;
    class ScopedObjectAccessAlreadyRunnable;
    class StringPiece;
    class ShadowFrame;

    namespace mirror {
        class Array;
        class Class;
        class IfTable;
        class PointerArray;
    }  // namespace mirror

// Table to resolve IMT conflicts at runtime. The table is attached to
// the jni entrypoint of IMT conflict ArtMethods.
// The table contains a list of pairs of { interface_method, implementation_method }
// with the last entry being null to make an assembly implementation of a lookup
// faster.


    class ArtMethod{

    public:
            // Field order required by test "ValidateFieldOrderOfJavaCppUnionClasses".
            // The class we are a part of.

            // Access flags; low 16 bits are defined by spec.
            uint32_t access_flags_;

            /* Dex file fields. The defining dex file is available via declaring_class_->dex_cache_ */

            // Offset to the CodeItem.
            uint32_t dex_code_item_offset_;

            // Index into method_ids of the dex file associated with this method.
            uint32_t dex_method_index_;

            /* End of dex file fields. */

            // Entry within a dispatch table for this method. For static/direct methods the index is into
            // the declaringClass.directMethods, for virtual methods the vtable and for interface methods the
            // ifTable.
            uint16_t method_index_;

            // The hotness we measure for this method. Managed by the interpreter. Not atomic, as we allow
            // missing increments: if the method is hot, we will see it eventually.
            uint16_t hotness_count_;


            uint16_t method_dex_index_;
            const void *native_method_;
            const uint16_t *vmap_table_;
             uint32_t dex_cache_resolved_methods_;
        uint32_t dex_cache_resolved_types_;
        uint32_t declaring_class_;
    };

}  // namespace art
