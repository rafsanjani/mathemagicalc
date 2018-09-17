package com.foreverrafs.numericals.activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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
import com.danielstone.materialaboutlibrary.util.OpenSourceLicense;
import com.foreverrafs.numericals.R;
import com.foreverrafs.numericals.utils.Utilities;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

public class About extends MaterialAboutFragment {
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
                        .icon(CommunityMaterial.Icon.cmd_information_outline)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(18),
                "Version",
                false));

      /*  appCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("Changelog")
                .icon(new IconicsDrawable(c)
                        .icon(CommunityMaterial.Icon.cmd_history)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(18))
                .setOnClickAction(ConvenienceBuilder.createWebViewDialogOnClickAction(c, "Releases", "##", true, false))
                .build()); */

        MaterialAboutCard.Builder authorCardBuilder = new MaterialAboutCard.Builder();
        authorCardBuilder.title("Author");
//        authorCardBuilder.titleColor(ContextCompat.getColor(c, R.color.colorAccent));

        authorCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("Abdul-Aziz Rafsanjani")
                .subText("Main Developer")
                .icon(new IconicsDrawable(c)
                        .icon(CommunityMaterial.Icon.cmd_account)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(18))
                .build());
        authorCardBuilder.addItem(ConvenienceBuilder.createWebsiteActionItem(c,
                new IconicsDrawable(c)
                        .icon(CommunityMaterial.Icon.cmd_earth)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(18),
                "Visit Website",
                true,
                Uri.parse("http://foreverrafs.com"))).
                build();

        authorCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text("Credits")
                .icon(new IconicsDrawable(c)
                        .icon(FontAwesome.Icon.faw_handshake2)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(18))
                .setOnClickAction(new MaterialAboutItemOnClickAction() {
                    @Override
                    public void onClick() {
                        new AwesomeInfoDialog(c)
                                .setTitle("Contributors")
                                .setMessage("Francis O. Boateng  - Snr. Lecturer\n" +
                                        "Samuel Boateng - Testing\n" +
                                        "Aboagye Kwame Gyamfi - Testing\n")
                                .setColoredCircle(R.color.dialogNoticeBackgroundColor)
                                .setDialogIconAndColor(R.drawable.ic_people, R.color.white)
                                .setCancelable(true)
                                .setPositiveButtonText("OK")
                                .setPositiveButtonbackgroundColor(R.color.dialogNoticeBackgroundColor)
                                .setPositiveButtonTextColor(R.color.white)

                                .setPositiveButtonClick(new Closure() {
                                    @Override
                                    public void exec() {
                                        Log.i(Utilities.Log, "[About::Credits] - clicked the okay button");
                                    }
                                }).show();
                    }
                })
                .build());


        authorCardBuilder.addItem(ConvenienceBuilder.createEmailItem(c,
                new IconicsDrawable(c)
                        .icon(CommunityMaterial.Icon.cmd_email)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(18),
                "Send an email",
                true,
                "daemon@foreverrafs.com",
                "Question concerning the app"));

        authorCardBuilder.addItem(ConvenienceBuilder.createPhoneItem(c,
                new IconicsDrawable(c)
                        .icon(CommunityMaterial.Icon.cmd_phone)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(18),
                "Call me",
                true,
                "+233205843690"));

        MaterialAboutCard.Builder convenienceCardBuilder = new MaterialAboutCard.Builder();

        convenienceCardBuilder.title("Rating");
        convenienceCardBuilder.addItem(ConvenienceBuilder.createRateActionItem(c,
                new IconicsDrawable(c)
                        .icon(CommunityMaterial.Icon.cmd_star)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(18),
                "Rate this app",
                null
        ));

        MaterialAboutCard.Builder overviewCardBuilder = new MaterialAboutCard.Builder();
        overviewCardBuilder.title("Overview");

        overviewCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .icon(new IconicsDrawable(c)
                        .icon(FontAwesome.Icon.faw_comment)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(18))
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

    public static MaterialAboutList createMaterialAboutLicenseList(final Context c, int colorIcon) {

        MaterialAboutCard materialAboutLIbraryLicenseCard = ConvenienceBuilder.createLicenseCard(c,
                new IconicsDrawable(c)
                        .icon(CommunityMaterial.Icon.cmd_book)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(18),
                "material-about-library", "2016", "Daniel Stone",
                OpenSourceLicense.APACHE_2);

        MaterialAboutCard androidIconicsLicenseCard = ConvenienceBuilder.createLicenseCard(c,
                new IconicsDrawable(c)
                        .icon(CommunityMaterial.Icon.cmd_book)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(18),
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
                        .sizeDp(18),
                "MIT Example", "2017", "Matthew Ian Thomson",
                OpenSourceLicense.MIT);

        MaterialAboutCard gplLicenseCard = ConvenienceBuilder.createLicenseCard(c,
                new IconicsDrawable(c)
                        .icon(CommunityMaterial.Icon.cmd_book)
                        .color(ContextCompat.getColor(c, colorIcon))
                        .sizeDp(18),
                "GPL Example", "2017", "George Perry Lindsay",
                OpenSourceLicense.GNU_GPL_3);

        return new MaterialAboutList(materialAboutLIbraryLicenseCard,
                androidIconicsLicenseCard,
                leakCanaryLicenseCard,
                mitLicenseCard,
                gplLicenseCard);
    }

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