/*
 * Copyright 2012-2013 Institut National des Sciences Appliquées de Lyon (INSA-Lyon)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dispatch;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.Clock;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkHistoryChart;
import com.carrotsearch.junitbenchmarks.annotation.BenchmarkMethodChart;
import com.carrotsearch.junitbenchmarks.annotation.LabelType;
import fr.insalyon.citi.golo.benchmarks.GoloBenchmark;
import org.jruby.embed.EmbedEvalUnit;
import org.jruby.embed.ScriptingContainer;
import org.junit.Test;

@BenchmarkOptions(clock = Clock.NANO_TIME, warmupRounds = 10)
@BenchmarkMethodChart(filePrefix = "monomorphic-dispatch")
@BenchmarkHistoryChart(filePrefix = "monomorphic-dispatch-history", labelWith = LabelType.TIMESTAMP)
public class MonomorphicTest extends GoloBenchmark {

  private static final Class<?> GoloModule = loadGoloModule("Monomorphic.golo");
  private static final Class<?> GroovyClass = loadGroovyClass("Monomorphic.groovy");
  private static final ScriptingContainer JRubyContainer;
  private static final EmbedEvalUnit JRubyScript;

  static {
    JRubyContainer = new ScriptingContainer();
    JRubyScript = jrubyEvalUnit(JRubyContainer, "monomorphic.rb");
  }

  @Test
  public void golo() throws Throwable {
    GoloModule.getMethod("run").invoke(null);
  }

  @Test
  public void golo_elvis() throws Throwable {
    GoloModule.getMethod("run_elvis").invoke(null);
  }

  @Test
  public void golo_elvis_nulls() throws Throwable {
    GoloModule.getMethod("run_elvis_with_nulls").invoke(null);
  }

  @Test
  public void groovy() throws Throwable {
    GroovyClass.getMethod("run").invoke(null);
  }

  @Test
  public void java_boxed() throws Throwable {
    Monomorphic.run_boxed();
  }

  @Test
  public void java_unboxed() throws Throwable {
    Monomorphic.run_unboxed();
  }

  @Test
  public void jruby() throws Throwable {
    JRubyScript.run();
  }
}
