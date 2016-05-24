# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('socialife', '0002_auto_20160508_0405'),
    ]

    operations = [
        migrations.DeleteModel(
            name='Usuario',
        ),
    ]
