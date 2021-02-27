package com.cingaldi.commons.resttools;

import com.cingaldi.commons.resttools.exceptions.MediaNotSupportedException;
import org.springframework.http.ResponseEntity;

import javax.print.attribute.standard.Media;
import java.util.function.Supplier;

public class BodyVersionSwitch {


    public static final String HEADER_MEDIA_PREFIX = "application/vnd.com.cingaldi.";

    public Version withVersion(String header, String defaultVersion) {

        if (!header.contains(HEADER_MEDIA_PREFIX)) {
            return new Version(defaultVersion, true);
        }

        String version = header.split("application/vnd.com.cingaldi.")[1].split("\\+")[0];
        return new Version(version, version.equals(defaultVersion));
    }

    /**
     * lets developer automatically create a kill switch: when enabled falls back on the default version handler
     *
     */
    public BodyVersionSwitch withKillSwitch(String name) {
        //TODO implement
        return this;
    }

    public class Version {
        private String version;
        private boolean isDefault;

        private Version(String version, boolean isDefault) {
            this.version = version;
            this.isDefault = isDefault;
        }

        public String getVersion() {
            return version;
        }

        public boolean isDefault() {
            return isDefault;
        }

        public MatchingVersion whenDefault(Supplier<ResponseEntity> cbk) {
            if(this.isDefault) {
                return new MatchingVersion(this.version, cbk.get());
            }

            return new MatchingVersion(this.version);
        }

        public MatchingVersion whenMatch(String version, Supplier<ResponseEntity> cbk) {

            if(version.equals(this.version)) {
                return new MatchingVersion(this.version, cbk.get());
            }

            return new MatchingVersion(this.version);
        }
    }

    public class MatchingVersion {
        private String actualVersion;
        private ResponseEntity matched;

        private MatchingVersion(String actualVersion) {
            this.actualVersion = actualVersion;
            this.matched = null;
        }
        public MatchingVersion(String actualVersion, ResponseEntity matched) {
            this.actualVersion = actualVersion;
            this.matched = matched;
        }

        public MatchingVersion orMatch(String version, Supplier<ResponseEntity> cbk) {

            if (matched == null && version.equals(actualVersion)) {
                return new MatchingVersion(this.actualVersion, cbk.get());
            }

            if(matched != null) {
                return new MatchingVersion(this.actualVersion, this.matched);
            }


            return new MatchingVersion(this.actualVersion);

        }

        public ResponseEntity getResult () {

            if (this.matched != null) {
                return this.matched;
            }

            throw new MediaNotSupportedException();
        }
    }
}
