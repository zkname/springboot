package com.zkname.patchca;

import org.patchca.background.SingleColorBackgroundFactory;
import org.patchca.color.SingleColorFactory;
import org.patchca.filter.predefined.CurvesRippleFilterFactory;
import org.patchca.font.RandomFontFactory;
import org.patchca.service.AbstractCaptchaService;
import org.patchca.text.renderer.BestFitTextRenderer;
import org.patchca.word.AdaptiveRandomWordFactory;

public class CaptchaService extends AbstractCaptchaService {

	public CaptchaService() {
		super.setBackgroundFactory(new SingleColorBackgroundFactory());
		AdaptiveRandomWordFactory arwf=new AdaptiveRandomWordFactory();
		arwf.setMinLength(4);
		arwf.setMaxLength(4);
		arwf.setCharacters("0123456789");
		super.setWordFactory(arwf);
		RandomFontFactory fontFactory = new RandomFontFactory();
		fontFactory.setMaxSize(30);
		fontFactory.setMinSize(20);
		super.setFontFactory(fontFactory);
		super.setTextRenderer(new BestFitTextRenderer());
		super.setColorFactory(new SingleColorFactory());
		super.setFilterFactory(new CurvesRippleFilterFactory(super.getColorFactory()));
		super.getTextRenderer().setLeftMargin(0);
		super.getTextRenderer().setRightMargin(0);
		super.setWidth(100);
		super.setHeight(35);
	}

}
