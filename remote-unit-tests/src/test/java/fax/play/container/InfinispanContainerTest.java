package fax.play.container;

import org.infinispan.client.hotrod.DefaultTemplate;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.RemoteCacheManagerAdmin;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.server.test.core.InfinispanContainer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InfinispanContainerTest {

   @Test
   public void gettingStarted() {
      try (InfinispanContainer container = new InfinispanContainer()) {
         container.start();

         try (RemoteCacheManager manager =
                      container.getRemoteCacheManager(new ConfigurationBuilder())) {
            RemoteCacheManagerAdmin admin = manager.administration();

            RemoteCache<String, String> cache = admin.createCache("mycache", DefaultTemplate.DIST_SYNC);

            cache.put("Hello", "World");

            String value = cache.get("Hello");
            assertThat(value).isEqualTo("World");
         }
      }
   }
}
