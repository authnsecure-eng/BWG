package com.pcmc.bwg.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Shared-secret used to authenticate server-to-server calls from the mobile
 * app backend's AdminBridgeClient (see SurveyIngestController). This is NOT
 * the admin JWT - it is a separate, simple API key because the caller is a
 * backend service, not a logged-in admin user. The endpoint should also be
 * restricted at the network/firewall level to internal traffic only (see
 * the hosting requirement spec, port 8090 must not be public).
 */
@ConfigurationProperties(prefix = "app.internal-bridge")
public class InternalBridgeProperties {

    private String apiKey;
    private boolean enabled = true;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
