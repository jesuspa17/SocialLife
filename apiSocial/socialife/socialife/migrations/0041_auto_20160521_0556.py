# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('socialife', '0040_auto_20160520_1247'),
    ]

    operations = [
        migrations.AlterField(
            model_name='plan',
            name='descripcion',
            field=models.TextField(max_length=2000),
        ),
    ]
