package com.foreverrafs.numericals.activities;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeInfoDialog;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;
import com.danielstone.materialaboutlibrary.ConvenienceBuilder;
import com.danielstone.materialaboutlibrary.MaterialAboutFragment;
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem;
import com.danielstone.materialaboutlibrary.items.MaterialAboutItemOnClickAction;
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem;
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard;
import com.danielstone.materialaboutlibrary.model.MaterialAboutList;
import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.utils.Utilities;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

public class About extends MaterialAboutFragment {
    private static final int iconSize = 20;

    @Nullable
    public static MaterialAboutList createMaterialAboutList(final Context c, final int colorIcon, final int theme) {

        MaterialAboutCard.Builder appCardBuilder = new MaterialAboutCard.Builder();
        // Add items to card

        appCardBuilder.addItem(new MaterialAboutTitleItem.Builder()
                .text("Mathemagi-Calc")
                .desc("© 2018 Abdul - Aziz Rafsanjani")
                .icon(R.mipmap.ic_launcher)
                .build());
        appCardBuilder.addItem(ConvenienceBuilder.createVersionActionItem(c,
                new IconicsDrawable(c)
                        .icon(FontAwesome.Icon.faw_info)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(iconSize),
                "Version",
                false));
        MaterialAboutCard.Builder authorCardBuilder = new MaterialAboutCard.Builder();
        authorCardBuilder.title("Author");

        authorCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("Abdul-Aziz Rafsanjani")
                .subText("Main Developer")
                .icon(new IconicsDrawable(c)
                        .icon(FontAwesome.Icon.faw_user)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(iconSize))
                .build());
        authorCardBuilder.addItem(ConvenienceBuilder.createWebsiteActionItem(c,
                new IconicsDrawable(c)
                        .icon(FontAwesome.Icon.faw_globe_africa)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(iconSize),
                "Visit Website",
                true,
                Uri.parse("http://foreverrafs.com"))).
                build();

        authorCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("Credits")
                .icon(new IconicsDrawable(c)
                        .icon(FontAwesome.Icon.faw_handshake)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(iconSize))
                .setOnClickAction(new MaterialAboutItemOnClickAction() {
                    @Override
                    public void onClick() {
                        new AwesomeInfoDialog(c)
                                .setTitle("Contributors")
                                .setMessage("1. Mr. Francis O. Boateng  - Snr. Lecturer\n" +
                                        "2. Nsafoa Boateng     -   Product Testing\n" +
                                        "3. Samuel Odoom       -   Euler Description\n" +
                                        "4. Aboagye Gyamfi     -   Product Testing\n" +
                                        "5. Annor  Anderson    -   Product Testing\n" +
                                        "6. Essel  Sylvester   -   Product Testing\n")

                                .setColoredCircle(R.color.dialogNoticeBackgroundColor)
                                .setDialogIconAndColor(R.drawable.ic_people, R.color.white)
                                .setDialogMessageColor(Color.BLACK)
                                .setCancelable(true)
                                .setPositiveButtonText("OK")
                                .setPositiveButtonbackgroundColor(R.color.dialogNoticeBackgroundColor)
                                .setPositiveButtonTextColor(R.color.white)
                                .setPositiveButtonClick(new Closure() {
                                    @Override
                                    public void exec() {
                                        Log.i(Utilities.LOG_TAG, "[About::Credits] - clicked the okay button");
                                    }
                                }).show();
                    }
                })
                .build());

        authorCardBuilder.addItem(ConvenienceBuilder.createEmailItem(c,
                new IconicsDrawable(c)
                        .icon(FontAwesome.Icon.faw_envelope)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(iconSize),
                "Send an email",
                true,
                "daemon@foreverrafs.com",
                "Question concerning the app"));

        authorCardBuilder.addItem(ConvenienceBuilder.createPhoneItem(c,
                new IconicsDrawable(c)
                        .icon(FontAwesome.Icon.faw_mobile_alt)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(iconSize),
                "Call me",
                true,
                "+233205843690"));

        MaterialAboutCard.Builder convenienceCardBuilder = new MaterialAboutCard.Builder();

        convenienceCardBuilder.title("Rating");
        convenienceCardBuilder.addItem(ConvenienceBuilder.createRateActionItem(c,
                new IconicsDrawable(c)
                        .icon(FontAwesome.Icon.faw_star)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(iconSize),
                "Rate this app",
                null
        ));

        MaterialAboutCard.Builder overviewCardBuilder = new MaterialAboutCard.Builder();
        overviewCardBuilder.title("Overview");

        overviewCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .icon(new IconicsDrawable(c)
                        .icon(FontAwesome.Icon.faw_comment)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(iconSize))
                .text("A Numerical Methods Suite")
                .subTextHtml("<p>This app is intended to be a Numerical Methods and Computation suite for students." +
                        "The topics covered in this version are:<p>" +
                        "<ol>" +
                        "<li>Numerical Conversions</li>" +
                        "<li>Location of Roots</li>" +
                        "<li>System of Equations</li>" +
                        "<li>Ordinary Differential Equations</li><br>" +
                        "</ol>" +
                        "Based on inspiration and ideas from <br><b>Mr. Francis O. Boateng</b> - Snr. Lecturer (UEW-K)")
                .setIconGravity(MaterialAboutActionItem.GRAVITY_TOP)
                .build()
        );

        return new MaterialAboutList(appCardBuilder.build(), authorCardBuilder.build(), convenienceCardBuilder.build(), overviewCardBuilder.build());
    }

    /*
        public static MaterialAboutList createMaterialAboutLicenseList(final Context c, int colorIcon) {

            MaterialAboutCard materialAboutLIbraryLicenseCard = ConvenienceBuilder.createLicenseCard(c,
                    new IconicsDrawable(c)
                            .icon(CommunityMaterial.Icon.cmd_book)
                            .color(ContextCompat.getColor(c, colorIcon))
                            .sizeDp(iconSize),
                    "material-about-library", "2016", "Daniel Stone",
                    OpenSourceLicense.APACHE_2);

            MaterialAboutCard androidIconicsLicenseCard = ConvenienceBuilder.createLicenseCard(c,
                    new IconicsDrawable(c)
                            .icon(CommunityMaterial.Icon.cmd_book)
                            .color(ContextCompat.getColor(c, colorIcon))
                            .sizeDp(iconSize),
                    "Android Iconics", "2016", "Mike Penz",
                    OpenSourceLicense.APACHE_2);

            MaterialAboutCard leakCanaryLicenseCard = ConvenienceBuilder.createLicenseCard(c,
                    new IconicsDrawable(c)
                            .icon(CommunityMaterial.Icon.cmd_book)
                            .color(ContextCompat.getColor(c, colorIcon))
                            .sizeDp(18),
                    "LeakCanary", "2015", "Square, Inc",
                    OpenSourceLicense.APACHE_2);

            MaterialAboutCard mitLicenseCard = ConvenienceBuilder.createLicenseCard(c,
                    new IconicsDrawable(c)
                            .icon(CommunityMaterial.Icon.cmd_book)
                            .color(ContextCompat.getColor(c, colorIcon))
                            .sizeDp(iconSize),
                    "MIT Example", "2017", "Matthew Ian Thomson",
                    OpenSourceLicense.MIT);

            MaterialAboutCard gplLicenseCard = ConvenienceBuilder.createLicenseCard(c,
                    new IconicsDrawable(c)
                            .icon(CommunityMaterial.Icon.cmd_book)
                            .color(ContextCompat.getColor(c, colorIcon))
                            .sizeDp(iconSize),
                    "GPL Example", "2017", "George Perry Lindsay",
                    OpenSourceLicense.GNU_GPL_3);

            return new MaterialAboutList(materialAboutLIbraryLicenseCard,
                    androidIconicsLicenseCard,
                    leakCanaryLicenseCard,
                    mitLicenseCard,
                    gplLicenseCard);
        }
     */
    @NonNull
    @Override
    protected MaterialAboutList getMaterialAboutList(@NonNull Context context) {
        return createMaterialAboutList(context, R.color.mal_color_icon_light_theme, R.style.Theme_Mal_Light);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}