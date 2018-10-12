package com.bsuleymen.rxjava2test.Model;

import java.util.List;

public class CitiesResponse {
    /**
     * predictions : [{"description":"Шымкент, Казахстан","id":"61ce37a5e8904897cb35f0fafdb4bf85eca4eaba","matched_substrings":[{"length":3,"offset":0}],"place_id":"ChIJu4FmVFbyqDgRmzZb54K1Z6E","reference":"ChIJu4FmVFbyqDgRmzZb54K1Z6E","structured_formatting":{"main_text":"Шымкент","main_text_matched_substrings":[{"length":3,"offset":0}],"secondary_text":"Казахстан"},"terms":[{"offset":0,"value":"Шымкент"},{"offset":9,"value":"Казахстан"}],"types":["locality","political","geocode"]},{"description":"Чумыр, Казахстан","id":"c5554e3b9f92ffc90f187a6b774c550a5c9cd06d","matched_substrings":[{"length":5,"offset":0}],"place_id":"ChIJCxK_tEoHf0IR76lqTCoqQSQ","reference":"ChIJCxK_tEoHf0IR76lqTCoqQSQ","structured_formatting":{"main_text":"Чумыр","main_text_matched_substrings":[{"length":5,"offset":0}],"secondary_text":"Казахстан"},"terms":[{"offset":0,"value":"Чумыр"},{"offset":7,"value":"Казахстан"}],"types":["locality","political","geocode"]},{"description":"Шымкудук, Казахстан","id":"a7b031c237813ea29a66ec9e048d08148bfaa19b","matched_substrings":[{"length":3,"offset":0}],"place_id":"ChIJ5YzcdlGbdEERJyy5oO7P_LA","reference":"ChIJ5YzcdlGbdEERJyy5oO7P_LA","structured_formatting":{"main_text":"Шымкудук","main_text_matched_substrings":[{"length":3,"offset":0}],"secondary_text":"Казахстан"},"terms":[{"offset":0,"value":"Шымкудук"},{"offset":10,"value":"Казахстан"}],"types":["locality","political","geocode"]}]
     * status : OK
     */
    private String error_message;

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    private String status;
    private List<PredictionsBean> predictions;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PredictionsBean> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<PredictionsBean> predictions) {
        this.predictions = predictions;
    }

    public static class PredictionsBean {
        /**
         * description : Шымкент, Казахстан
         * id : 61ce37a5e8904897cb35f0fafdb4bf85eca4eaba
         * matched_substrings : [{"length":3,"offset":0}]
         * place_id : ChIJu4FmVFbyqDgRmzZb54K1Z6E
         * reference : ChIJu4FmVFbyqDgRmzZb54K1Z6E
         * structured_formatting : {"main_text":"Шымкент","main_text_matched_substrings":[{"length":3,"offset":0}],"secondary_text":"Казахстан"}
         * terms : [{"offset":0,"value":"Шымкент"},{"offset":9,"value":"Казахстан"}]
         * types : ["locality","political","geocode"]
         */

        private String description;
        private String id;
        private String place_id;
        private String reference;
        private StructuredFormattingBean structured_formatting;
        private List<MatchedSubstringsBean> matched_substrings;
        private List<TermsBean> terms;
        private List<String> types;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPlace_id() {
            return place_id;
        }

        public void setPlace_id(String place_id) {
            this.place_id = place_id;
        }

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        public StructuredFormattingBean getStructured_formatting() {
            return structured_formatting;
        }

        public void setStructured_formatting(StructuredFormattingBean structured_formatting) {
            this.structured_formatting = structured_formatting;
        }

        public List<MatchedSubstringsBean> getMatched_substrings() {
            return matched_substrings;
        }

        public void setMatched_substrings(List<MatchedSubstringsBean> matched_substrings) {
            this.matched_substrings = matched_substrings;
        }

        public List<TermsBean> getTerms() {
            return terms;
        }

        public void setTerms(List<TermsBean> terms) {
            this.terms = terms;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }

        public static class StructuredFormattingBean {
            /**
             * main_text : Шымкент
             * main_text_matched_substrings : [{"length":3,"offset":0}]
             * secondary_text : Казахстан
             */

            private String main_text;
            private String secondary_text;
            private List<MainTextMatchedSubstringsBean> main_text_matched_substrings;

            public String getMain_text() {
                return main_text;
            }

            public void setMain_text(String main_text) {
                this.main_text = main_text;
            }

            public String getSecondary_text() {
                return secondary_text;
            }

            public void setSecondary_text(String secondary_text) {
                this.secondary_text = secondary_text;
            }

            public List<MainTextMatchedSubstringsBean> getMain_text_matched_substrings() {
                return main_text_matched_substrings;
            }

            public void setMain_text_matched_substrings(List<MainTextMatchedSubstringsBean> main_text_matched_substrings) {
                this.main_text_matched_substrings = main_text_matched_substrings;
            }

            public static class MainTextMatchedSubstringsBean {
                /**
                 * length : 3
                 * offset : 0
                 */

                private int length;
                private int offset;

                public int getLength() {
                    return length;
                }

                public void setLength(int length) {
                    this.length = length;
                }

                public int getOffset() {
                    return offset;
                }

                public void setOffset(int offset) {
                    this.offset = offset;
                }
            }
        }

        public static class MatchedSubstringsBean {
            /**
             * length : 3
             * offset : 0
             */

            private int length;
            private int offset;

            public int getLength() {
                return length;
            }

            public void setLength(int length) {
                this.length = length;
            }

            public int getOffset() {
                return offset;
            }

            public void setOffset(int offset) {
                this.offset = offset;
            }
        }

        public static class TermsBean {
            /**
             * offset : 0
             * value : Шымкент
             */

            private int offset;
            private String value;

            public int getOffset() {
                return offset;
            }

            public void setOffset(int offset) {
                this.offset = offset;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }
}
