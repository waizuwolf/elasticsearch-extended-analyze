/*
 * Copyright 2013 Jun Ohtani
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package info.johtani.elasticsearch.action.admin.indices.extended.analyze;

import org.elasticsearch.action.ActionRequestValidationException;
import org.elasticsearch.action.support.single.shard.SingleShardRequest;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;

import java.io.IOException;

import static org.elasticsearch.action.ValidateActions.*;

public class ExtendedAnalyzeRequest extends SingleShardRequest<ExtendedAnalyzeRequest> {

    private String[] text;
    private String analyzer;
    private String tokenizer;
    private String[] tokenFilters;
    private String[] charFilters;
    private String field;
    /**
     * specified output attribute names
     */
    private String[] attributes;

    /**
     * output short attribute names, only class name
     */
    private boolean shortAttributeName = false;


    ExtendedAnalyzeRequest() {

    }

    /**
     * Constructs a new analyzer request for the provided text.
     *
     * @param index The index name
     */
    public ExtendedAnalyzeRequest(String index) {
        this.index(index);
    }


    public String[] text() {
        return this.text;
    }

    public ExtendedAnalyzeRequest text(String... text) {
        this.text = text;
        return this;
    }

    public ExtendedAnalyzeRequest analyzer(String analyzer) {
        this.analyzer = analyzer;
        return this;
    }

    public String analyzer() {
        return this.analyzer;
    }

    public ExtendedAnalyzeRequest tokenizer(String tokenizer) {
        this.tokenizer = tokenizer;
        return this;
    }

    public String tokenizer() {
        return this.tokenizer;
    }

    public ExtendedAnalyzeRequest tokenFilters(String... tokenFilters) {
        this.tokenFilters = tokenFilters;
        return this;
    }

    public String[] tokenFilters() {
        return this.tokenFilters;
    }

    public ExtendedAnalyzeRequest charFilters(String... charFilters) {
        this.charFilters = charFilters;
        return this;
    }
    public String[] charFilters() {
        return this.charFilters;
    }

    public ExtendedAnalyzeRequest attributes(String... attributes) {
        this.attributes = attributes;
        return this;
    }

    public String[] attributes() {
        return this.attributes;
    }

    public ExtendedAnalyzeRequest field(String field) {
        this.field = field;
        return this;
    }

    public String field() {
        return this.field;
    }

    public boolean shortAttributeName() {
        return this.shortAttributeName;
    }

    public ExtendedAnalyzeRequest shortAttributeName(boolean shortAttributeName) {
        this. shortAttributeName = shortAttributeName;
        return this;
    }

    @Override
    public ActionRequestValidationException validate() {
        ActionRequestValidationException validationException = null;
        if (text == null) {
            validationException = addValidationError("text is missing", validationException);
        }
        return validationException;
    }

    @Override
    public void readFrom(StreamInput in) throws IOException {
        super.readFrom(in);
        text = in.readStringArray();
        analyzer = in.readOptionalString();
        tokenizer = in.readOptionalString();
        int size = in.readVInt();
        if (size > 0) {
            tokenFilters = new String[size];
            for (int i = 0; i < size; i++) {
                tokenFilters[i] = in.readString();
            }
        }
        field = in.readOptionalString();
        shortAttributeName = in.readBoolean();
        int attSize = in.readVInt();
        if (size > 0) {
            attributes = new String[attSize];
            for (int i = 0; i < attSize; i++) {
                attributes[i] = in.readString();
            }
        }
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        super.writeTo(out);
        out.writeStringArray(text);
        out.writeOptionalString(analyzer);
        out.writeOptionalString(tokenizer);
        if (tokenFilters == null) {
            out.writeVInt(0);
        } else {
            out.writeVInt(tokenFilters.length);
            for (String tokenFilter : tokenFilters) {
                out.writeString(tokenFilter);
            }
        }
        out.writeOptionalString(field);
        out.writeBoolean(shortAttributeName);
        if (attributes == null) {
            out.writeVInt(0);
        } else {
            out.writeVInt(attributes.length);
            for (String attribute : attributes) {
                out.writeString(attribute);
            }
        }
    }
}
