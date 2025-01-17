/********************************************************************************
 * <eCharacter> is a research project of the <e-UCM>
 *          research group.
 *
 *    Developed by: Alejandro Muñoz del Rey, Sergio de Luis Nieto and David González
 *    Ledesma.
 *    Under the supervision of Baltasar Fernández-Manjón and Javier Torrente
 * 
 *    Copyright 2012-2013 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://echaracter.e-ucm.es>, 
 *          <http://e-adventure.e-ucm.es> or <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 *      <eCharacter> is free software: you can 
 *      redistribute it and/or modify it under the terms of the GNU Lesser 
 *      General Public License as published by the Free Software Foundation, 
 *      either version 3 of the License, or (at your option) any later version.
 *  
 *      <eCharacter> is distributed in the hope that it 
 *      will be useful, but WITHOUT ANY WARRANTY; without even the implied 
 *      warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 *      See the GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <eCharacter>. If not, 
 *      see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package es.eucm.echaracter.gui.slider;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.AbstractController;
import de.lessvoid.nifty.controls.NextPrevHelper;
import de.lessvoid.nifty.controls.Slider;
import de.lessvoid.nifty.controls.SliderChangedEvent;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.input.NiftyMouseInputEvent;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.tools.SizeValue;
import de.lessvoid.xml.xpp3.Attributes;
import java.util.Properties;

/**
 * @deprecated Please use {@link de.lessvoid.nifty.controls.Slider} when accessing NiftyControls.
 */
@Deprecated
public class SliderControl extends AbstractController implements Slider {
  private SliderImpl sliderImpl = new SliderImpl();
  private SliderView sliderView;
  private Nifty nifty;
  private Element element;
  private Element elementPosition;
  private Element elementBackground;
  private NextPrevHelper nextPrevHelper;
  private float min;
  private float max;
  private float initial;
  private float stepSize;
  private float buttonStepSize;

  @Override
  public void bind(
      final Nifty nifty,
      final Screen screen,
      final Element element,
      final Properties parameter,
      final Attributes controlDefinitionAttributes) {
    super.bind(element);

    this.nifty = nifty;
    this.element = element;
    elementBackground = element.findElementByName("#background");
    elementPosition = element.findElementByName("#position");
    nextPrevHelper = new NextPrevHelper(element, screen.getFocusHandler());

    if ("verticalSlidereCharacter".equals(parameter.getProperty("name"))) {
      sliderView = new SliderViewVertical(this);
    } else if ("horizontalSlidereCharacter".equals(parameter.getProperty("name"))) {
      sliderView = new SliderViewHorizontal(this);
    }

    min = Float.valueOf(parameter.getProperty("min", "0.0"));
    max = Float.valueOf(parameter.getProperty("max", "100.0"));
    initial = Float.valueOf(parameter.getProperty("initial", "0.0"));
    stepSize = Float.valueOf(parameter.getProperty("stepSize", "1.0"));
    buttonStepSize = Float.valueOf(parameter.getProperty("buttonStepSize", "25.0"));
    sliderImpl.bindToView(sliderView, min, max, stepSize, buttonStepSize);
    sliderImpl.setValue(initial);
  }

  @Override
  public void onStartScreen() {
  }

  @Override
  public void layoutCallback() {
    sliderImpl.updateView();
  }

  @Override
  public boolean inputEvent(final NiftyInputEvent inputEvent) {
    if (nextPrevHelper.handleNextPrev(inputEvent)) {
      return true;
    }
    if (inputEvent == NiftyInputEvent.MoveCursorUp || inputEvent == NiftyInputEvent.MoveCursorLeft) {
      sliderImpl.stepDown();
      return true;
    } else if (inputEvent == NiftyInputEvent.MoveCursorDown || inputEvent == NiftyInputEvent.MoveCursorRight) {
      sliderImpl.stepUp();
      return true;
    }
    return false;
  }

  public void upClick() {
    sliderImpl.stepDown();
  }

  public void downClick() {
    sliderImpl.stepUp();
  }

  public void mouseClick(final int mouseX, final int mouseY) {
    sliderImpl.setValueFromPosition(
        mouseX - elementBackground.getX() - elementPosition.getWidth() / 2,
        mouseY - elementBackground.getY() - elementPosition.getHeight() / 2);
  }

  public void mouseWheel(final Element element, final NiftyMouseInputEvent inputEvent) {
    int mouseWheel = inputEvent.getMouseWheel();
    float currentValue = sliderImpl.getValue();
    if (mouseWheel < 0) {
      sliderImpl.setValue(currentValue - sliderImpl.getButtonStepSize() * mouseWheel);
    } else if (mouseWheel > 0) {
      sliderImpl.setValue(currentValue - sliderImpl.getButtonStepSize() * mouseWheel);
    }
  }

  // Slider implementation

  @Override
  public void setValue(final float value) {
    sliderImpl.setValue(value);
  }

  @Override
  public float getValue() {
    return sliderImpl.getValue();
  }

  @Override
  public void setMin(final float min) {
    sliderImpl.setMin(min);
  }

  @Override
  public float getMin() {
    return sliderImpl.getMin();
  }

  @Override
  public void setMax(final float max) {
    sliderImpl.setMax(max);
  }

  @Override
  public float getMax() {
    return sliderImpl.getMax();
  }

  @Override
  public void setStepSize(final float stepSize) {
    sliderImpl.setStepSize(stepSize);
  }

  @Override
  public float getStepSize() {
    return sliderImpl.getStepSize();
  }

  @Override
  public void setButtonStepSize(final float buttonStepSize) {
    sliderImpl.setButtonStepSize(buttonStepSize);
  }

  @Override
  public float getButtonStepSize() {
    return sliderImpl.getButtonStepSize();
  }

  @Override
  public void setup(final float min, final float max, final float current, final float stepSize, final float buttonStepSize) {
    sliderImpl.setup(min, max, current, stepSize, buttonStepSize);
  }

  // SliderView implementation

  private class SliderViewVertical implements SliderView {
    private Slider slider;

    public SliderViewVertical(final Slider slider) {
      this.slider = slider;
    }

    @Override
    public int getSize() {
      return elementBackground.getHeight() - elementPosition.getHeight();
    }

    @Override
    public void update(final int position) {
      elementPosition.setConstraintY(new SizeValue(position + "px"));
      elementBackground.layoutElements();
    }

    @Override
    public int filter(final int pixelX, final int pixelY) {
      return pixelY;
    }

    @Override
    public void valueChanged(final float value) {
      if (element.getId() != null) {
        nifty.publishEvent(element.getId(), new SliderChangedEvent(slider, value));
      }
    }
  }

  private class SliderViewHorizontal implements SliderView {
    private Slider slider;

    public SliderViewHorizontal(final Slider slider) {
      this.slider = slider;
    }

    @Override
    public int getSize() {
      return elementBackground.getWidth() - elementPosition.getWidth();
    }

    @Override
    public void update(final int position) {
      elementPosition.setConstraintX(new SizeValue(position + "px"));
      elementBackground.layoutElements();
    }

    @Override
    public int filter(final int pixelX, final int pixelY) {
      return pixelX;
    }

    @Override
    public void valueChanged(final float value) {
      if (element.getId() != null) {
        nifty.publishEvent(element.getId(), new SliderChangedEvent(slider, value));
      }
    }
  }
}
