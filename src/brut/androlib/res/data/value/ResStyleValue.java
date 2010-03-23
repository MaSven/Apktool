/*
 *  Copyright 2010 Ryszard Wiśniewski <brut.alll@gmail.com>.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package brut.androlib.res.data.value;

import brut.androlib.AndrolibException;
import brut.androlib.res.data.ResResSpec;
import brut.androlib.res.data.ResResource;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import org.xmlpull.v1.XmlSerializer;

/**
 * @author Ryszard Wiśniewski <brut.alll@gmail.com>
 */
public class ResStyleValue extends ResBagValue implements ResXmlSerializable {
    public ResStyleValue(ResReferenceValue parent,
            Map<ResReferenceValue, ResScalarValue> items) {
        super(parent, items);
    }

    public void serializeToXml(XmlSerializer serializer, ResResource res)
            throws IOException, AndrolibException {
        serializer.startTag(null, "style");
        serializer.attribute(null, "name", res.getResSpec().getName());
        if (! mParent.isNull()) {
            serializer.attribute(null, "parent", mParent.toResXmlFormat());
        }
        for (Entry<ResReferenceValue, ResScalarValue> entry
                : mItems.entrySet()) {
            ResResSpec spec = entry.getKey().getReferent();
            ResAttr attr = (ResAttr) spec.getDefaultResource().getValue();
            String value = attr.convertToResXmlFormat(entry.getValue());

            if (value == null) {
                continue;
            }

            serializer.startTag(null, "item");
            serializer.attribute(null, "name",
                spec.getFullName(res.getResSpec().getPackage(), true));
            serializer.text(value);
            serializer.endTag(null, "item");
        }
        serializer.endTag(null, "style");
    }
}