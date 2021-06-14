package edu.fbansept.demo.android.blocnote.utils.requestmanager;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Base64;
import android.webkit.MimeTypeMap;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Map;

public class ImageUpload extends StringRequest {

    private final String imageStringBase64;

    Context context;

    public ImageUpload(Context context, Bitmap bitmap, String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(Request.Method.POST, url, listener, errorListener);
        this.context = context;

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);


        //encode le fichier au format base64 "URL safe",
        //car le serveur utilisera : Base64.getUrlDecoder().decode(base64Str);
        byte[] encoded = Base64.encode(
                stream.toByteArray(), Base64.URL_SAFE | Base64.NO_PADDING | Base64.NO_WRAP);

        imageStringBase64 = "data:image/jpeg;base64," + new String(encoded);
    }

    public ImageUpload(Context context, Uri uri, String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) throws IOException {
        super(Request.Method.POST, url, listener, errorListener);
        this.context = context;

        String extension = getMimeType(uri);
        InputStream is = context.getContentResolver().openInputStream(uri);


        String prefix = "";

        if(extension.equals("image/jpeg")){
            prefix = "data:image/jpeg;base64,";
        } else if(extension.equals("image/png")){
            prefix = "data:image/png;base64,";
        } else {
            throw new IOException("L'image doit être en png ou en jpg");
        }

        //encode le fichier au format base64 "URL safe",
        //car le serveur utilisera : Base64.getUrlDecoder().decode(base64Str);
        byte[] encoded = Base64.encode(
                IOUtils.toByteArray(is), Base64.URL_SAFE | Base64.NO_PADDING | Base64.NO_WRAP);

        imageStringBase64 = prefix + new String(encoded);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {

        Map<String, String> params = new Hashtable<>();

        params.put("image", imageStringBase64);
        return params;
    }


    public String getMimeType(Uri uri) {
        String mimeType = null;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = context.getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }

}
